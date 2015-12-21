package com.angarron.sfvframedata.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.angarron.sfvframedata.R;
import com.angarron.sfvframedata.application.VFramesApplication;

import data.model.CharacterID;


public class CharacterSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_character_select);
        setupToolbar();
        setupClickListeners();
    }

    private void setupClickListeners() {
        CharacterCardClickListener characterCardClickListener = new CharacterCardClickListener();
        GridLayout charactersGrid = (GridLayout) findViewById(R.id.character_select_gridlayout);
        for (int i = 0; i < charactersGrid.getChildCount(); i++) {
            charactersGrid.getChildAt(i).setOnClickListener(characterCardClickListener);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Select A Character");
        }
    }

    private class CharacterCardClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            CharacterID clickedCharacter;

            switch (view.getId()) {
                case R.id.birdie_card:
                    clickedCharacter = CharacterID.BIRDIE;
                    break;
                case R.id.cammy_card:
                    clickedCharacter = CharacterID.CAMMY;
                    break;
                case R.id.ryu_card:
                    clickedCharacter = CharacterID.RYU;
                    break;
                case R.id.chun_card:
                    clickedCharacter = CharacterID.CHUN;
                    break;
                case R.id.dictator_card:
                    clickedCharacter = CharacterID.DICTATOR;
                    break;
                case R.id.nash_card:
                    clickedCharacter = CharacterID.NASH;
                    break;
                case R.id.fang_card:
                case R.id.laura_card:
                case R.id.karin_card:
                case R.id.mika_card:
                case R.id.zangief_card:
                case R.id.necalli_card:
                case R.id.claw_card:
                case R.id.dhalsim_card:
                case R.id.ken_card:
                case R.id.rashid_card:
                default:
                    Log.e(VFramesApplication.APP_LOGGING_TAG, "clicked invalid character card");
                    return;
            }

            Intent intent = new Intent(CharacterSelectActivity.this, CharacterSummaryActivity.class);
            intent.putExtra(CharacterSummaryActivity.INTENT_EXTRA_TARGET_CHARACTER, clickedCharacter);
            startActivity(intent);
        }
    }
}
