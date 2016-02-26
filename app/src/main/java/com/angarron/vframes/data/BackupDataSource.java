package com.angarron.vframes.data;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import data.json.model.VFramesDataJsonAdapter;
import data.model.IDataModel;

//This implementation of IVFramesDataSource uses test data
//which is packaged with the app.
public class BackupDataSource implements IDataSource {

    private static final String LOCAL_DATA_FILE_NAME = "vframes_local_data.json";

    private Context context;

    public BackupDataSource(Context context) {
        this.context = context;
    }

    @Override
    public void fetchData(Listener listener) {
        new GetDataTask().execute(listener);
    }

    private class GetDataTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... objects) {

            Listener listener = (Listener) objects[0];

            if (!localDataFileExists()) {
                initializeLocalFile();
            }

            try {
                IDataModel dataModel = readFromBackupFile();
                listener.onDataReceived(dataModel, false);
            } catch (IOException e) {
                listener.onDataFetchFailed(FetchFailureReason.READ_FROM_FILE_FAILED);
            }

            return null;
        }
    }

    private IDataModel readFromBackupFile() throws IOException {
        Log.d("findme", "reading from backup file");
        File file = context.getFileStreamPath(LOCAL_DATA_FILE_NAME);
        String dataString = FileUtils.readFileToString(file);
        JsonParser parser = new JsonParser();
        Log.d("findme", "converting to json");
        JsonObject jsonData = parser.parse(dataString).getAsJsonObject();
        Log.d("findme", "parsing json");
        return VFramesDataJsonAdapter.jsonToDataModel(jsonData);
    }

    private boolean localDataFileExists() {
        File file = context.getFileStreamPath(LOCAL_DATA_FILE_NAME);
        Log.d("findme", "local file exists: " + file.exists());
        return file.exists();
    }

    private void initializeLocalFile() {
        Log.d("findme", "initializing local file");
        byte[] bundledData = loadBundledData();

        try {
            FileOutputStream outputStream = context.openFileOutput(LOCAL_DATA_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(bundledData);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("could not initialize local file");
        }
    }

    private byte[] loadBundledData() {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("default_data_model", "raw", context.getPackageName());
        InputStream inputStream = resources.openRawResource(identifier);

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dataString = writer.toString();
        return dataString.getBytes();
    }

}
