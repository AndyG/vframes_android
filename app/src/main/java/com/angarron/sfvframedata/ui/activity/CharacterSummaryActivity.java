package com.angarron.sfvframedata.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
            ImageView summaryCharacterImage = (ImageView) findViewById(R.id.summary_character_image);
            summaryCharacterImage.setImageResource(getCharacterDrawableResource());
            TextView summaryAccentBar = (TextView) findViewById(R.id.summary_accent_bar);
            summaryAccentBar.setText(getNameResource());
            summaryAccentBar.setBackgroundResource(getCharacterAccentColor());
        }
    }

    private int getCharacterDrawableResource() {
        switch(targetCharacter) {
            case RYU:
                return R.drawable.ryu_card;
            case CHUN:
                return R.drawable.chun_card;
            case DICTATOR:
                return R.drawable.dictator_card;
            case BIRDIE:
                return R.drawable.birdie_card;
            case NASH:
                return R.drawable.nash_card;
            case CAMMY:
                return R.drawable.cammy_card;
            /*
            case KEN:
                return R.drawable.ken_card;
            case MIKA:
                return R.drawable.mika_card;
            case NECALLI:
                return R.drawable.necalli_card;
            case CLAW:
                return R.drawable.claw_card;
            case RASHID:
                return R.drawable.rashid_card;
            case KARIN:
                return R.drawable.karin_card;
            case LAURA:
                return R.drawable.laura_card;
            case DHALSIM:
                return R.drawable.dhalsim_card;
            case ZANGIEF:
                return R.drawable.zangief_card;
            case FANG:
                return R.drawable.fang_card;
            */
            default:
                return -1;
        }
    }

    private int getCharacterAccentColor() {
        switch(targetCharacter) {
            case RYU:
                return R.color.ryu_accent;
            case CHUN:
                return R.color.chun_accent;
            case DICTATOR:
                return R.color.dictator_accent;
            case BIRDIE:
                return R.color.birdie_accent;
            case NASH:
                return R.color.nash_accent;
            case CAMMY:
                return R.color.cammy_accent;
            /*
            case KEN:
                return R.color.ken_accent;
            case MIKA:
                return R.color.mika_accent;
            case NECALLI:
                return R.color.necalli_accent;
            case CLAW:
                return R.color.claw_accent;
            case RASHID:
                return R.color.rashid_accent;
            case KARIN:
                return R.color.karin_accent;
            case LAURA:
                return R.color.laura_accent;
            case DHALSIM:
                return R.color.dhalsim_accent;
            case ZANGIEF:
                return R.color.zangief_accent;
            case FANG:
                return R.color.fang_accent;
            */
            default:
                return -1;
        }
    }

    private int getNameResource() {
        switch(targetCharacter) {
            case RYU:
                return R.string.ryu_name;
            case CHUN:
                return R.string.chun_name;
            case DICTATOR:
                return R.string.dictator_name;
            case BIRDIE:
                return R.string.birdie_name;
            case NASH:
                return R.string.nash_name;
            case CAMMY:
                return R.string.cammy_name;
            /*
            case KEN:
                return R.string.ken_name;
            case MIKA:
                return R.string.mika_name;
            case NECALLI:
                return R.string.necalli_name;
            case CLAW:
                return R.string.claw_name;
            case RASHID:
                return R.string.rashid_name;
            case KARIN:
                return R.string.karin_name;
            case LAURA:
                return R.string.laura_name;
            case DHALSIM:
                return R.string.dhalsim_name;
            case ZANGIEF:
                return R.string.zangief_name;
            case FANG:
                return R.string.fang_name;
            */
            default:
                return -1;
        }
    }
}
