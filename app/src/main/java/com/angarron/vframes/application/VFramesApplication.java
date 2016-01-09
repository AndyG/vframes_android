package com.angarron.vframes.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.R;
import com.angarron.vframes.data.IDataSource;
import com.angarron.vframes.data.NetworkFallbackDataSource;
import com.angarron.vframes.data.TestDataSource;
import com.angarron.vframes.network.VFramesRESTApi;
import com.crashlytics.android.Crashlytics;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import data.model.IDataModel;
import io.fabric.sdk.android.Fabric;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class VFramesApplication extends Application {

    public static final String APP_LOGGING_TAG = "VFrames";
    private static final String PREFERENCE_FILE_KEY = "com.agarron.vframes.PREFERENCE_FILE_KEY";
    private static final String APP_LAUNCH_COUNT_KEY = "APP_LAUNCH_COUNT_KEY";

    private IDataSource dataSource;
    private IDataModel dataModel;

    @Override
    public void onCreate() {
        super.onCreate();

        //Crashlytics Integration
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        //SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        int appLaunchCount = sharedPreferences.getInt(APP_LAUNCH_COUNT_KEY, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt(APP_LAUNCH_COUNT_KEY, appLaunchCount + 1);
        sharedPreferencesEditor.apply();

        //No need to use data from the network yet.
        init(R.raw.vframes_data);
    }

    private void init(int resourceId) {
        dataSource = new TestDataSource(getResources(), resourceId);
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
}
