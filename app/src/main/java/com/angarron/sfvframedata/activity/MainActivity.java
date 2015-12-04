package com.angarron.sfvframedata.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.agarron.vframestestdata.testdata.CharacterFactory;
import com.angarron.sfvframedata.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import data.json.character.SFCharacterJsonAdapter;
import data.model.character.SFCharacter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.generateRandomDataButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.generateRandomDataButton:
                JsonObject dataJson = generateRandomData();
                Log.d("MainActivity", "character json: " + dataJson.toString());
                break;
        }
    }

    private JsonObject generateRandomData() {
        JsonArray charactersArray = new JsonArray();
        CharacterFactory characterFactory = new CharacterFactory();
        for (int i = 0; i < 1; i++) {
            SFCharacter character = characterFactory.generateCharacter(5);
            JsonObject characterJson = SFCharacterJsonAdapter.CharacterToJson(character);
            charactersArray.add(characterJson);
        }

        JsonObject dataJson = new JsonObject();
        dataJson.add("characters", charactersArray);
        return dataJson;
    }
}
