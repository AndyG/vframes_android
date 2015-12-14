package com.angarron.sfvframedata.data;

import android.util.Log;

import com.angarron.sfvframedata.application.VFramesApplication;
import com.angarron.sfvframedata.network.VFramesRESTApi;
import com.google.gson.JsonObject;

import data.json.model.VFramesDataJsonAdapter;
import data.model.IDataModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by andy on 12/13/15
 */
public class NetworkFallbackDataSource implements IDataSource {
    
    private VFramesRESTApi restApi;

    public NetworkFallbackDataSource(VFramesRESTApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public void fetchData(final Listener listener) {
        Call<JsonObject> call = restApi.getData();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                IDataModel dataModel = VFramesDataJsonAdapter.jsonToDataModel(response.body());
                Log.d(VFramesApplication.APP_LOGGING_TAG, "read characters");
                listener.onDataReceived(dataModel);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(VFramesApplication.APP_LOGGING_TAG, "failed to retrieve data", t);
                //TODO: try to load the data from local backup
            }
        });
    }
}
