package com.angarron.vframes.network;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.util.CrashlyticsUtil;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetNewestDataVersionTask {

    public void fetchData(final IGetDataVersionListener listener) {

        VFramesRESTApi restApi = createRESTApi();
        Call<JsonObject> call = restApi.getDataVersion("android");
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
                    CrashlyticsUtil.logException(new Throwable("failed to load data model version, unsuccessful response: " + errorMessage));
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

    private void processSuccessfulResponse(JsonObject body, IGetDataVersionListener listener) {
        try {
            int version = body.get("dataVersion").getAsInt();
            int minSupportedAndroidVersion = body.get("minSupportedAndroidVersion").getAsInt();
            listener.onSuccess(version, minSupportedAndroidVersion);
        } catch (Exception e) {
            CrashlyticsUtil.logException(e);
            listener.onFailure();
        }
    }

    private VFramesRESTApi createRESTApi() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl("http://still-hollows-20653.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
            retrofitBuilder.client(client);
        }

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(VFramesRESTApi.class);
    }

    public interface IGetDataVersionListener {
        void onSuccess(int dataVersion, int minSupportedAndroidVersion);
        void onFailure();
    }
}
