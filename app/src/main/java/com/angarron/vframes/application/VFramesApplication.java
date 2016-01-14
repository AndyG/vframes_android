package com.angarron.vframes.application;

import android.app.Application;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.data.IDataSource;
import com.angarron.vframes.data.TestDataSource;
import com.crashlytics.android.Crashlytics;

import data.model.IDataModel;
import io.fabric.sdk.android.Fabric;

public class VFramesApplication extends Application {

    public static final String APP_LOGGING_TAG = "VFrames";

    private IDataSource dataSource;
    private IDataModel dataModel;

    @Override
    public void onCreate() {
        super.onCreate();

        //Crashlytics Integration
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        init();
    }

    private void init() {
        dataSource = new TestDataSource(getResources(), getPackageName());
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
