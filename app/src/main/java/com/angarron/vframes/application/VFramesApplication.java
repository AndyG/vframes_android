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
    private static final String VERSION_LAUNCH_COUNT_KEY = "VERSION_LAUNCH_COUNT_KEY_" + BuildConfig.VERSION_CODE;

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
        incrementAppLaunchCount();
        incrementVersionLaunchCount();

        //If this is the first time launching this version, we want to use the data packaged
        //with this version.
        if (getVersionLaunchCount() == 1) {
            init(true);
        } else {
            init(false);
        }
    }

    private int getVersionLaunchCount() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(VERSION_LAUNCH_COUNT_KEY, 0);
    }

    private void incrementVersionLaunchCount() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        int versionLaunchCount = sharedPreferences.getInt(VERSION_LAUNCH_COUNT_KEY, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt(VERSION_LAUNCH_COUNT_KEY, versionLaunchCount + 1);
        sharedPreferencesEditor.apply();
    }

    private void incrementAppLaunchCount() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        int appLaunchCount = sharedPreferences.getInt(APP_LAUNCH_COUNT_KEY, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt(APP_LAUNCH_COUNT_KEY, appLaunchCount + 1);
        sharedPreferencesEditor.apply();
    }

    private void init(boolean clearLocalData) {
        //When starting app, use backup data source
        //When we add network updates to startup, this will change
        dataSource = new BackupDataSource(this);
        if (clearLocalData) {
            dataSource.clearLocalData();
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
}
