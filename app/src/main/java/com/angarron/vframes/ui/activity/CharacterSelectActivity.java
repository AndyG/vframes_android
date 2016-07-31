package com.angarron.vframes.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.angarron.vframes.R;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.util.FeedbackUtil;

import data.model.CharacterID;


public class CharacterSelectActivity extends NavigationHostActivity {

    public static final String INTENT_EXTRA_WAS_UPDATED = "WAS_UPDATED";

    private static final String PREFERENCE_FILE_KEY = "com.agarron.vframes.PREFERENCE_FILE_KEY";
    private static final String APP_LAUNCH_COUNT_KEY = "APP_LAUNCH_COUNT_KEY";

    private static final String REVIEW_REQUEST_SEEN = "REVIEW_REQUEST_SEEN";
    private static final String CAN_COMPARE_CHARACTERS_SEEN = "CAN_COMPARE_CHARACTERS_SEEN";
    private static final String ENHANCED_TOURNAMENT_MATCHES_SEEN = "ENHANCED_TOURNAMENT_MATCHES_SEEN";

    private CharacterID highlightedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        if (savedInstanceState == null) {
            boolean wasUpdated = getIntent().getBooleanExtra(INTENT_EXTRA_WAS_UPDATED, false);
            if (wasUpdated) {
                showDataUpdatedDialog();
            } else {
                showWelcomeDialog();
            }
        }

        verifyDataAvailable();
        setupToolbar();
        setupClickListeners();
    }

    private void showWelcomeDialog() {
        if (shouldShowReviewRequestDialog()) {
            showReviewRequestDialog();
        } else if (shouldShowCanCompareDialog()) {
            showCanCompareDialog();
        } else if (shouldShowTournamentMatchesDialog()) {
            //TODO: remove this in the next version
            showTournamentMatchesDialog();
        }
    }

    private void showTournamentMatchesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.enhanced_tournament_matches_message)
                .setTitle(R.string.enhanced_tournament_matches_title);

        builder.setPositiveButton(R.string.ok_thanks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //no-op
            }
        });

        AlertDialog dialog = builder.create();

        //Users often immediately touch the screen when they enter the CharacterSelectActivity,
        //which would result in accidentally dismissing the dialog without reading it.
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    private boolean shouldShowTournamentMatchesDialog() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        int appLaunchCount = sharedPreferences.getInt(APP_LAUNCH_COUNT_KEY, 0);
        boolean canTakeNotesSeen = sharedPreferences.getBoolean(ENHANCED_TOURNAMENT_MATCHES_SEEN, false);

        if(appLaunchCount >= 2 && !canTakeNotesSeen) {
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(ENHANCED_TOURNAMENT_MATCHES_SEEN, true);
            sharedPreferencesEditor.apply();
            return true;
        }

        return false;
    }

    private void showCanCompareDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.can_compare_message)
                .setTitle(R.string.can_compare_title);

        builder.setPositiveButton(R.string.ok_thanks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //no-op
            }
        });

        AlertDialog dialog = builder.create();

        //Users often immediately touch the screen when they enter the CharacterSelectActivity,
        //which would result in accidentally dismissing the dialog without reading it.
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    private boolean shouldShowCanCompareDialog() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        int appLaunchCount = sharedPreferences.getInt(APP_LAUNCH_COUNT_KEY, 0);
        boolean reviewRequestSeen = sharedPreferences.getBoolean(CAN_COMPARE_CHARACTERS_SEEN, false);

        if(appLaunchCount >= 2 && !reviewRequestSeen) {
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(CAN_COMPARE_CHARACTERS_SEEN, true);
            sharedPreferencesEditor.apply();
            return true;
        }

        return false;
    }

    private void showDataUpdatedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.data_updated_message)
                .setTitle(R.string.data_updated_title);

        builder.setPositiveButton(R.string.ok_thanks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //no-op
            }
        });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_character_select, menu);
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

    private boolean shouldShowReviewRequestDialog() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        int appLaunchCount = sharedPreferences.getInt(APP_LAUNCH_COUNT_KEY, 0);
        boolean reviewRequestSeen = sharedPreferences.getBoolean(REVIEW_REQUEST_SEEN, false);

        if(appLaunchCount >= 5 && !reviewRequestSeen) {
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(REVIEW_REQUEST_SEEN, true);
            sharedPreferencesEditor.apply();
            return true;
        }

        return false;
    }

    private void showReviewRequestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.rating_request_message)
                .setTitle(R.string.rating_request_title);

        builder.setPositiveButton(R.string.rating_request_positive_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the Rate button
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            }
        });

        builder.setNegativeButton(R.string.rating_request_negative_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();

        //Users often immediately touch the screen when they enter the CharacterSelectActivity,
        //which would result in accidentally dismissing the dialog without reading it.
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    private void setupClickListeners() {
        CharacterCardClickListener characterCardClickListener = new CharacterCardClickListener();
        GridLayout charactersGrid = (GridLayout) findViewById(R.id.character_select_gridlayout);
        for (int i = 0; i < charactersGrid.getChildCount(); i++) {
            charactersGrid.getChildAt(i).setOnClickListener(characterCardClickListener);
            charactersGrid.getChildAt(i).setOnLongClickListener(characterCardClickListener);
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

    private class CharacterCardClickListener implements View.OnClickListener, View.OnLongClickListener {

        @Override
        public void onClick(View view) {
            CharacterID clickedCharacter = getClickedCharacter(view.getId());

            if (highlightedCharacter == null) {
                startCharacterActivity(clickedCharacter, (ImageView) getImageViewForCharacter(clickedCharacter));
            } else {
                startCharacterComparisonActivity(highlightedCharacter, clickedCharacter);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            //If no character is selected, select this character
            CharacterID clickedCharacter = getClickedCharacter(view.getId());

            if (highlightedCharacter == null) {
                highlightCharacter(clickedCharacter);
            } else if (highlightedCharacter != clickedCharacter) {
                unhighlightCharacter();
                highlightCharacter(clickedCharacter);
            } else {
                unhighlightCharacter();
            }

            return true;
        }
    }

    private void highlightCharacter(CharacterID characterId) {
        highlightedCharacter = characterId;
        ImageView imageView = (ImageView) getImageViewForCharacter(highlightedCharacter);
        imageView.setSelected(true);
    }

    private void startCharacterComparisonActivity(CharacterID firstCharacter, CharacterID secondCharacter) {
        unhighlightCharacter();

        Intent intent = new Intent(this, CharacterComparisonActivity.class);
        intent.putExtra(CharacterComparisonActivity.INTENT_EXTRA_FIRST_CHARACTER, firstCharacter);
        intent.putExtra(CharacterComparisonActivity.INTENT_EXTRA_SECOND_CHARACTER, secondCharacter);

        if (shouldAnimateTransition()) {

            Pair<View, String> first = new Pair<>(getImageViewForCharacter(firstCharacter), getString(R.string.comparison_first_character_transition));
            Pair<View, String> second = new Pair<>(getImageViewForCharacter(secondCharacter), getString(R.string.comparison_second_character_transition));

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    first, second);

            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private void unhighlightCharacter() {
        Log.d("findme", "unselected character");
        ImageView imageView = (ImageView) getImageViewForCharacter(highlightedCharacter);
        imageView.setSelected(false);
        highlightedCharacter = null;
    }

    private void startCharacterActivity(CharacterID clickedCharacter, ImageView imageViewForCharacter) {
        Intent intent = new Intent(CharacterSelectActivity.this, CharacterSummaryActivity.class);
        intent.putExtra(CharacterSummaryActivity.INTENT_EXTRA_TARGET_CHARACTER, clickedCharacter);

        if (shouldAnimateTransition()) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CharacterSelectActivity.this,
                    imageViewForCharacter,
                    getString(R.string.character_select_transition));

            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private CharacterID getClickedCharacter(int viewId) {
        switch (viewId) {
            case R.id.juri_card:
                return CharacterID.JURI;
            case R.id.boxer_card:
                return CharacterID.BOXER;
            case R.id.ibuki_card:
                return CharacterID.IBUKI;
            case R.id.guile_card:
                return CharacterID.GUILE;
            case R.id.alex_card:
                return CharacterID.ALEX;
            case R.id.birdie_card:
                return CharacterID.BIRDIE;
            case R.id.cammy_card:
                return CharacterID.CAMMY;
            case R.id.ryu_card:
                return CharacterID.RYU;
            case R.id.chun_card:
                return CharacterID.CHUN;
            case R.id.dictator_card:
                return CharacterID.DICTATOR;
            case R.id.nash_card:
                return CharacterID.NASH;
            case R.id.fang_card:
                return CharacterID.FANG;
            case R.id.laura_card:
                return CharacterID.LAURA;
            case R.id.karin_card:
                return CharacterID.KARIN;
            case R.id.mika_card:
                return CharacterID.MIKA;
            case R.id.zangief_card:
                return CharacterID.ZANGIEF;
            case R.id.necalli_card:
                return CharacterID.NECALLI;
            case R.id.claw_card:
                return CharacterID.CLAW;
            case R.id.dhalsim_card:
                return CharacterID.DHALSIM;
            case R.id.ken_card:
                return CharacterID.KEN;
            case R.id.rashid_card:
                return CharacterID.RASHID;
            default:
                throw new RuntimeException("clicked invalid character card");
        }
    }

    private boolean shouldAnimateTransition() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }

        //We don't animate the transition on landscape since there is no target image in landscape.
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if (rotation != Surface.ROTATION_0 && rotation != Surface.ROTATION_180) {
            return false;
        }

        return true;
    }

    private View getImageViewForCharacter(CharacterID characterID) {
        switch (characterID) {
            case JURI:
                return findViewById(R.id.juri_image_view);
            case BOXER:
                return findViewById(R.id.boxer_image_view);
            case IBUKI:
                return findViewById(R.id.ibuki_image_view);
            case GUILE:
                return findViewById(R.id.guile_image_view);
            case ALEX:
                return findViewById(R.id.alex_image_view);
            case RYU:
                return findViewById(R.id.ryu_image_view);
            case CHUN:
                return findViewById(R.id.chun_image_view);
            case DICTATOR:
                return findViewById(R.id.dictator_image_view);
            case BIRDIE:
                return findViewById(R.id.birdie_image_view);
            case NASH:
                return findViewById(R.id.nash_image_view);
            case CAMMY:
                return findViewById(R.id.cammy_image_view);
            case CLAW:
                return findViewById(R.id.claw_image_view);
            case LAURA:
                return findViewById(R.id.laura_image_view);
            case KEN:
                return findViewById(R.id.ken_image_view);
            case NECALLI:
                return findViewById(R.id.necalli_image_view);
            case RASHID:
                return findViewById(R.id.rashid_image_view);
            case MIKA:
                return findViewById(R.id.mika_image_view);
            case ZANGIEF:
                return findViewById(R.id.zangief_image_view);
            case FANG:
                return findViewById(R.id.fang_image_view);
            case DHALSIM:
                return findViewById(R.id.dhalsim_image_view);
            case KARIN:
                return findViewById(R.id.karin_image_view);
            default:
                throw new IllegalArgumentException("invalid character clicked: " + characterID.toString());
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
