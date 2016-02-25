package com.angarron.vframes.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.data.BackupDataSource;
import com.angarron.vframes.data.IDataSource;
import com.crashlytics.android.Crashlytics;

import data.model.IDataModel;
import io.fabric.sdk.android.Fabric;

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

        init();
    }

    private void init() {
        //When starting app, use backup data source
        //When we add network updates to startup, this will change
        dataSource = new BackupDataSource(this);
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
