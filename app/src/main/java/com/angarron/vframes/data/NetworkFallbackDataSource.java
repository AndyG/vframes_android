package com.angarron.vframes.data;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;

import com.angarron.vframes.network.VFramesRESTApi;
import com.google.gson.Gson;
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
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class NetworkFallbackDataSource implements IDataSource {

    private static final String LOCAL_DATA_FILE_NAME = "vframes_local_data.json";

    private VFramesRESTApi restApi;
    private Integer versionCode;
    private Context context;

    public NetworkFallbackDataSource(Integer versionCode, Context context) {
        restApi = createRESTApi();
        this.versionCode = versionCode;
        this.context = context;

        if (!localDataFileExists()) {
            initializeLocalFile();
        }
    }

    @Override
    public void fetchData(final Listener listener) {
        final IDataModel backupDataModel;
        try {
            backupDataModel = readFromBackupFile();
        } catch (IOException e) {
            listener.onDataFetchFailed(FetchFailureReason.READ_FROM_FILE_FAILED);
            return;
        }

        Call<JsonObject> call = restApi.getData(versionCode);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    processSuccessfulResponse(response.body(), backupDataModel, listener);
                } else {
                    try {
                        Log.d("findme", "error response: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.d("findme", "error parsing error response");

                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("findme", "error loading data: " + t.getMessage());
                //On failure, use data from the backup file.
                listener.onDataFetchFailed(FetchFailureReason.NETWORK_ERROR);
            }
        });
    }

    @Override
    public void clearLocalData() {
        initializeLocalFile();
    }

    private void processSuccessfulResponse(JsonObject body, IDataModel backupDataModel, Listener listener) {
        if (body.has("error")) {
            listener.onDataFetchFailed(FetchFailureReason.UNSUPPORTED_CLIENT_VERSION);
            return;
        }

        Integer newDataVersion = body.get("version").getAsInt();
        if (newDataVersion > backupDataModel.getVersion()) {
            //Overwrite the local file for use next time
            writeToBackupFile(body);
            //Provide the rest of the app with the new data
            IDataModel dataModel = VFramesDataJsonAdapter.jsonToDataModel(body);
            listener.onDataReceived(dataModel, true);
        } else {
            listener.onDataReceived(backupDataModel, false);
        }
    }

    private void writeToBackupFile(final JsonObject body) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //Convert the JsonObject body to a byte array
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(body);
                    byte[] data = jsonString.getBytes();

                    //Write the data to the local file.
                    FileOutputStream outputStream;
                    outputStream = context.openFileOutput(LOCAL_DATA_FILE_NAME, Context.MODE_PRIVATE);
                    outputStream.write(data);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private IDataModel readFromBackupFile() throws IOException {
        File file = context.getFileStreamPath(LOCAL_DATA_FILE_NAME);
        String dataString = FileUtils.readFileToString(file);
        JsonParser parser = new JsonParser();
        JsonObject jsonData = parser.parse(dataString).getAsJsonObject();
        return VFramesDataJsonAdapter.jsonToDataModel(jsonData);
    }

    private boolean localDataFileExists() {
        File file = context.getFileStreamPath(LOCAL_DATA_FILE_NAME);
        return file.exists();
    }

    private void initializeLocalFile() {
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

    private VFramesRESTApi createRESTApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://agarron.com/res/vframes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(VFramesRESTApi.class);
    }

}
