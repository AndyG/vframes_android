package com.angarron.sfvframedata.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.angarron.sfvframedata.R;
import com.angarron.sfvframedata.model.character.SFCharacter;
import com.angarron.sfvframedata.model.testdata.CharacterFactory;
import com.angarron.sfvframedata.network.json.character.SFCharacterJsonAdapter;
import com.google.gson.JsonObject;

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
                CharacterFactory characterFactory = new CharacterFactory();
                SFCharacter character = characterFactory.generateCharacter(5);

                JsonObject characterJson = SFCharacterJsonAdapter.CharacterToJson(character);
                Log.d("MainActivity", "character json: " + characterJson.toString());
                break;
        }
    }
}
