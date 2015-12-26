package com.angarron.vframes.data;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Looper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import data.json.model.VFramesDataJsonAdapter;

//This implementation of IVFramesDataSource uses test data
//which is packaged with the app.
public class TestDataSource implements IDataSource {

    private Resources resources;
    private int resourceId;

    public TestDataSource(Resources resources, int resourceId) {
        this.resources = resources;
        this.resourceId = resourceId;
    }

    @Override
    public void fetchData(Listener listener) {
        new GetDataTask().execute(listener);
    }

    private class GetDataTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... objects) {

            Looper.prepare();

            Listener listener = (Listener) objects[0];

            InputStream inputStream = resources.openRawResource(resourceId);

            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(inputStream, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String dataString = writer.toString();
            JsonParser parser = new JsonParser();
            JsonObject jsonData = parser.parse(dataString).getAsJsonObject();
            listener.onDataReceived(VFramesDataJsonAdapter.jsonToDataModel(jsonData));
            return null;
        }
    }
}
