package com.angarron.sfvframedata.application;

import android.app.Application;

import com.angarron.sfvframedata.R;
import com.angarron.sfvframedata.data.IDataSource;
import com.angarron.sfvframedata.data.IDataModel;
import com.angarron.sfvframedata.data.TestDataSource;

public class VFramesApplication extends Application {

    public static final String APP_LOGGING_TAG = "VFrames";

    private IDataSource dataSource;
    private IDataModel dataModel;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        //TODO: use real data source for real app
        dataSource = new TestDataSource(getResources(), R.raw.test_data);
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
