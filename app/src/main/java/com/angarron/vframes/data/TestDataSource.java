package com.angarron.vframes.data;

import android.content.res.Resources;
import android.os.AsyncTask;

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

    private static final String[] characterNames = new String[]{
            "ryu",
            "chun",
            "dictator",
            "birdie",
            "nash",
            "cammy",
            "claw",
            "laura",
            "ken",
            "necalli",
            "rashid",
            "mika",
            "zangief",
            "fang",
            "dhalsim",
            "karin"
    };

    private Resources resources;
    private String packageName;

    public TestDataSource(Resources resources, String packageName) {
        this.resources = resources;
        this.packageName = packageName;
    }

    @Override
    public void fetchData(Listener listener) {
        new GetDataTask().execute(listener);
    }

    private class GetDataTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... objects) {

            Listener listener = (Listener) objects[0];

            JsonObject jsonData = new JsonObject();

            JsonObject charactersJson = readCharactersJson();
            jsonData.add("characters", charactersJson);

            listener.onDataReceived(VFramesDataJsonAdapter.jsonToDataModel(jsonData));
            return null;
        }
    }

    private JsonObject readCharactersJson() {
        JsonObject charactersJson = new JsonObject();

        for (String characterName : characterNames) {
            JsonObject characterJson = readCharacterFile(characterName);
            charactersJson.add(characterName, characterJson);
        }

        return charactersJson;
    }

    private JsonObject readCharacterFile(String characterName) {
        int identifier = resources.getIdentifier(characterName + "_data", "raw", packageName);
        InputStream inputStream = resources.openRawResource(identifier);

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dataString = writer.toString();
        return new JsonParser().parse(dataString).getAsJsonObject();
    }
}
