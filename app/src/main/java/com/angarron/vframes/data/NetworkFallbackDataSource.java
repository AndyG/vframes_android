package com.angarron.vframes.data;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.network.VFramesRESTApi;
import com.angarron.vframes.util.CrashlyticsUtil;
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
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkFallbackDataSource implements IDataSource {

    private static final String LOCAL_DATA_FILE_NAME = "vframes_local_data.json";

    private VFramesRESTApi restApi;
    private Context context;

    public NetworkFallbackDataSource(Context context) {
        restApi = createRESTApi();
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

        Call<JsonObject> call = restApi.getData("android");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    processSuccessfulResponse(response.body(), backupDataModel, listener);
                } else {
                    String errorMessage;
                    try {
                         errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        errorMessage = e.getMessage();
                    }
                    CrashlyticsUtil.logException(new Throwable("failed to load data model, unsuccessful response: " + errorMessage));
                    listener.onDataFetchFailed(FetchFailureReason.NETWORK_ERROR);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CrashlyticsUtil.logException(t);
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
        if (!body.has("minSupportedAndroidVersion") ||
                body.get("minSupportedAndroidVersion").getAsInt() > BuildConfig.VERSION_CODE) {
            listener.onDataFetchFailed(FetchFailureReason.UNSUPPORTED_CLIENT_VERSION);
            return;
        }

        Integer newDataVersion = body.get("version").getAsInt();
        if (newDataVersion > backupDataModel.getVersion()) {
            //Load the data first to make sure it works
            IDataModel dataModel = VFramesDataJsonAdapter.jsonToDataModel(body);
            //Overwrite the local file for use next time
            writeToBackupFile(body);
            //Provide the rest of the app with the new data
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
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl("http://still-hollows-20653.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
            retrofitBuilder.client(client);
        }

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(VFramesRESTApi.class);
    }

}
