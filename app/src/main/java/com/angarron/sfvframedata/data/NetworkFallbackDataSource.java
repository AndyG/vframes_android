package com.angarron.sfvframedata.data;

import android.content.res.Resources;
import android.util.Log;

import com.angarron.sfvframedata.application.VFramesApplication;
import com.angarron.sfvframedata.network.VFramesRESTApi;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

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
    private Resources resources;
    private int backupResourceId;

    public NetworkFallbackDataSource(VFramesRESTApi restApi, Resources resources, int backupResourceId) {
        this.restApi = restApi;
        this.resources = resources;
        this.backupResourceId = backupResourceId;
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
                InputStream inputStream = resources.openRawResource(backupResourceId);

                StringWriter writer = new StringWriter();
                try {
                    IOUtils.copy(inputStream, writer);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onDataFetchFailed(FetchFailureReason.READ_FROM_FILE_FAILED);
                }
                String dataString = writer.toString();

                JsonParser parser = new JsonParser();
                JsonObject jsonData = parser.parse(dataString).getAsJsonObject();
                IDataModel dataModel = VFramesDataJsonAdapter.jsonToDataModel(jsonData);
                listener.onDataReceived(dataModel);
            }
        });
    }
}
