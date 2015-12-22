package com.angarron.sfvframedata.application;

import android.app.Application;

import com.angarron.sfvframedata.BuildConfig;
import com.angarron.sfvframedata.R;
import com.angarron.sfvframedata.data.IDataSource;
import com.angarron.sfvframedata.data.NetworkFallbackDataSource;
import com.angarron.sfvframedata.data.TestDataSource;
import com.angarron.sfvframedata.network.VFramesRESTApi;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import data.model.IDataModel;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class VFramesApplication extends Application {

    public static final String APP_LOGGING_TAG = "VFrames";

    private IDataSource dataSource;
    private IDataModel dataModel;

    @Override
    public void onCreate() {
        super.onCreate();
        init(false, R.raw.test_data);
    }

    private void init(boolean useRealData, int resourceId) {
        //TODO: use real data source for real app
        if (useRealData) {
            VFramesRESTApi restApi = getRestApi();
            dataSource = new NetworkFallbackDataSource(restApi, getResources(), resourceId);
        } else {
            dataSource = new TestDataSource(getResources(), resourceId);
        }
    }

    public IDataSource getDataSource() {
        return dataSource;
    }

    public IDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(IDataModel dataModel) {
        this.dataModel = dataModel;
    }

    private VFramesRESTApi getRestApi() {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor.Level logLevel = BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
        interceptor.setLevel(logLevel);
        client.interceptors().add(interceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://www.agarron.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        return retrofit.create(VFramesRESTApi.class);
    }
}
