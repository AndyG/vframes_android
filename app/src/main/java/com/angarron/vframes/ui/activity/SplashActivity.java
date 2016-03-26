package com.angarron.vframes.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.R;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.data.IDataSource;
import com.angarron.vframes.network.GetNewDataTask;
import com.angarron.vframes.network.GetNewestDataVersionTask;

import data.model.IDataModel;

//This activity is meant to be shown while loading data.
public class SplashActivity extends Activity {

    private static final int AVAILABLE_DATA_VERSION_FAILED = -1;

    private VFramesApplication application;

    private Integer availableDataVersion;
    private IDataModel currentDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GetNewestDataVersionTask getDataVersionTask = new GetNewestDataVersionTask();
        getDataVersionTask.getNewestVersion(new GetDataVersionListener());
        loadData();
    }

    private void loadData() {
        application = (VFramesApplication) getApplication();
        IDataSource dataSource = application.getDataSource();
        dataSource.fetchData(new DataSourceListener());
    }

    //This method handles waiting for loading both the current data model, and the version
    //of the newest model over the network. If the newest version is newer than the one we have,
    //we notify the user on the next Activity.
    private synchronized void launchCharacterSelectIfReady() {
        if (currentDataModel != null && availableDataVersion != null) {
            boolean updateFound = availableDataVersion > currentDataModel.getVersion();
            if (updateFound) {
                GetNewDataTask getNewDataTask = new GetNewDataTask(this);
                getNewDataTask.fetchData(new GetNewDataTask.IGetNewDataListener() {
                    @Override
                    public void onSuccess(IDataModel dataModel) {
                        application.setDataModel(dataModel);
                        launchCharacterSelectActivity(true);
                    }

                    @Override
                    public void onFailure() {
                        launchCharacterSelectActivity(false);
                    }
                });
            } else {
                launchCharacterSelectActivity(false);
            }
        }
    }

    private class DataSourceListener implements IDataSource.Listener {

        @Override
        public void onDataReceived(IDataModel data, boolean wasUpdated) {
            //Store the data in the application model and launch the HomeScreenActivity.
            application.setDataModel(data);
            currentDataModel = data;
            launchCharacterSelectIfReady();
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

    private void launchCharacterSelectActivity(boolean newVersionAvailable) {
        Intent launchCharacterSelectIntent = new Intent(this, CharacterSelectActivity.class);
        launchCharacterSelectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchCharacterSelectIntent.putExtra(CharacterSelectActivity.INTENT_EXTRA_WAS_UPDATED, newVersionAvailable);
        startActivity(launchCharacterSelectIntent);
        finish();
    }

    private void showUnsupportedVersionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsupported_client_version_message)
                .setTitle(R.string.unsupported_client_version_title);

        builder.setPositiveButton(R.string.visit_play_store, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                finish();
            }
        });

        builder.setNegativeButton(R.string.no_thanks, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();

        //Users often immediately touch the screen when they enter the CharacterSelectActivity,
        //which would result in accidentally dismissing the dialog without reading it.
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private class GetDataVersionListener implements GetNewestDataVersionTask.IGetDataVersionListener {
        @Override
        public void onSuccess(int dataVersion, int minSupportedAndroidVersion) {
            if (minSupportedAndroidVersion > BuildConfig.VERSION_CODE) {
                showUnsupportedVersionDialog();
            } else {
                availableDataVersion = dataVersion;
                launchCharacterSelectIfReady();
            }
        }

        @Override
        public void onFailure() {
            availableDataVersion = AVAILABLE_DATA_VERSION_FAILED;
            launchCharacterSelectIfReady();
        }
    }
}
