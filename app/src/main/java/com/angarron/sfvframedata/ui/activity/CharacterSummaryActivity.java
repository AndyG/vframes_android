package com.angarron.sfvframedata.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.angarron.sfvframedata.R;
import com.angarron.sfvframedata.application.VFramesApplication;

import data.model.CharacterName;

//This activity will house a ViewSwitcher which will have
//move list and frame data for the selected character.
public class CharacterSummaryActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_TARGET_CHARACTER = "INTENT_EXTRA_TARGET_CHARACTER";

    private CharacterName targetCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_summary);

        try {
            targetCharacter = (CharacterName) getIntent().getSerializableExtra(INTENT_EXTRA_TARGET_CHARACTER);
        } catch (ClassCastException e) {
            Log.e(VFramesApplication.APP_LOGGING_TAG, "failed to parse intent", e);
            finish();
        }

        //Load the toolbar based on the target character
        setupToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                Log.e(VFramesApplication.APP_LOGGING_TAG, "invalid menu item clicked: " + item.getItemId());
                return false;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.summary_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
