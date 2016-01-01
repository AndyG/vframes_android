package com.angarron.vframes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.angarron.vframes.R;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.util.FeedbackUtil;

import data.model.CharacterID;


public class CharacterSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);
        verifyDataAvailable();
        setupToolbar();
        setupClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                FeedbackUtil.sendFeedback(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    clickedCharacter = CharacterID.FANG;
                    break;
                case R.id.laura_card:
                    clickedCharacter = CharacterID.LAURA;
                    break;
                case R.id.karin_card:
                    clickedCharacter = CharacterID.KARIN;
                    break;
                case R.id.mika_card:
                    clickedCharacter = CharacterID.MIKA;
                    break;
                case R.id.zangief_card:
                    clickedCharacter = CharacterID.ZANGIEF;
                    break;
                case R.id.necalli_card:
                    clickedCharacter = CharacterID.NECALLI;
                    break;
                case R.id.claw_card:
                    clickedCharacter = CharacterID.CLAW;
                    break;
                case R.id.dhalsim_card:
                    clickedCharacter = CharacterID.DHALSIM;
                    break;
                case R.id.ken_card:
                    clickedCharacter = CharacterID.KEN;
                    break;
                case R.id.rashid_card:
                    clickedCharacter = CharacterID.RASHID;
                    break;
                default:
                    throw new RuntimeException("clicked invalid character card");
            }

            Intent intent = new Intent(CharacterSelectActivity.this, CharacterSummaryActivity.class);
            intent.putExtra(CharacterSummaryActivity.INTENT_EXTRA_TARGET_CHARACTER, clickedCharacter);
            startActivity(intent);
        }
    }

    private void verifyDataAvailable() {
        VFramesApplication application = (VFramesApplication) getApplication();
        if (application.getDataModel() == null) {
            Intent startSplashIntent = new Intent(this, SplashActivity.class);
            startActivity(startSplashIntent);
            finish();
        }
    }

}
