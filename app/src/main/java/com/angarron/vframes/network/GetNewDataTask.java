package com.angarron.vframes.network;

import android.content.Context;
import android.os.Handler;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.util.CrashlyticsUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import data.json.model.VFramesDataJsonAdapter;
import data.model.IDataModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetNewDataTask {

    private static final String LOCAL_DATA_FILE_NAME = "vframes_local_data.json";

    private Context context;

    public GetNewDataTask(Context context) {
        this.context = context;
    }

    public void fetchData(final IGetNewDataListener listener) {

        VFramesRESTApi restApi = createRESTApi();
        Call<JsonObject> call = restApi.getData("android");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    processSuccessfulResponse(response.body(), listener);
                } else {
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        errorMessage = e.getMessage();
                    }
                    CrashlyticsUtil.logException(new Throwable("failed to load data model, unsuccessful response: " + errorMessage));
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CrashlyticsUtil.logException(t);
                //On failure, use data from the backup file.
                listener.onFailure();
            }
        });
    }

    private void processSuccessfulResponse(JsonObject body, IGetNewDataListener listener) {
        try {
            IDataModel dataModel = VFramesDataJsonAdapter.jsonToDataModel(body);
            writeToBackupFile(body);
            listener.onSuccess(dataModel);
        } catch (Exception e) {
            CrashlyticsUtil.logException(e);
            listener.onFailure();
        }
    }

    private VFramesRESTApi createRESTApi() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl("http://still-hollows-20653.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        clientBuilder.readTimeout(5, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            clientBuilder.addInterceptor(loggingInterceptor).build();
        }

        retrofitBuilder.client(clientBuilder.build());

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(VFramesRESTApi.class);
    }

    private void writeToBackupFile(final JsonObject body) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //Convert the JsonObject body to a byte array
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(body);
                    byte[] data = jsonString.getBytes();

                    //Write the data to the local file.
                    FileOutputStream outputStream;
                    outputStream = context.openFileOutput(LOCAL_DATA_FILE_NAME, Context.MODE_PRIVATE);
                    outputStream.write(data);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface IGetNewDataListener {
        void onSuccess(IDataModel dataModel);
        void onFailure();
    }
}
