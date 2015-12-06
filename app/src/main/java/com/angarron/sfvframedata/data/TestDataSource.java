package com.angarron.sfvframedata.data;

import android.content.res.Resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import data.json.model.CharactersModelJsonAdapter;
import data.model.ICharactersModel;

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
        InputStream inputStream = resources.openRawResource(resourceId);

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onDataFetchFailed(FetchFailureReason.READ_FROM_FILE_FAILED);
        }
        String dataString = writer.toString();

        JsonParser parser = new JsonParser();
        JsonObject jsonData = parser.parse(dataString).getAsJsonObject();
        ICharactersModel charactersModel = CharactersModelJsonAdapter.jsonToCharactersModel(jsonData);
        listener.onDataReceived(new DataModel(charactersModel));
    }


}
