package com.angarron.sfvframedata.data;

import android.content.SharedPreferences;

//This implementation of IVFramesDataSource first tries to get new data
//over the network.
//
// If that is successful, it returns that data. Otherwise, it falls back to SharedPrefs.
public class SharedPrefsFallbackDataSource implements IDataSource {

    private static final String VFRAMES_DATA_PATH = "VFRAMES_DATA";

    private SharedPreferences sharedPreferences;

    public SharedPrefsFallbackDataSource(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void fetchData(Listener listener) {
        //TODO: implement this
    }
}
