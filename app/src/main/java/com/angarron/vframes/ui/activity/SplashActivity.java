package com.angarron.vframes.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.angarron.vframes.R;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.data.IDataSource;

import data.model.IDataModel;

//This activity is meant to be shown while loading data either
//over the network or from shared preferences.
public class SplashActivity extends Activity {

    private VFramesApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadData();
    }

    private void loadData() {
        application = (VFramesApplication) getApplication();
        IDataSource dataSource = application.getDataSource();
        dataSource.fetchData(new DataSourceListener());
    }

    private class DataSourceListener implements IDataSource.Listener {

        @Override
        public void onDataReceived(IDataModel data, boolean wasUpdated) {
            //Store the data in the application model and launch the HomeScreenActivity.
            application.setDataModel(data);
            launchHomeScreenActivity(wasUpdated);
        }

        @Override
        public void onDataFetchFailed(IDataSource.FetchFailureReason failureReason) {
            switch (failureReason) {
                case UNSUPPORTED_CLIENT_VERSION:
                case UNKNOWN_ERROR:
                case READ_FROM_FILE_FAILED:
                default:
                    throw new RuntimeException("failed to fetch data: " + failureReason);
            }
        }
    }

    private void launchHomeScreenActivity(boolean wasUpdated) {
        Intent launchHomeScreenIntent = new Intent(this, CharacterSelectActivity.class);
        launchHomeScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(launchHomeScreenIntent);
        finish();
    }
}
