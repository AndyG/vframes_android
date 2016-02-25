package com.angarron.vframes.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.R;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.util.FeedbackUtil;

import data.model.CharacterID;


public class CharacterSelectActivity extends AppCompatActivity {

    private static final String PREFERENCE_FILE_KEY = "com.agarron.vframes.PREFERENCE_FILE_KEY";
    private static final String APP_LAUNCH_COUNT_KEY = "APP_LAUNCH_COUNT_KEY";
    private static final String REVIEW_REQUEST_SEEN = "REVIEW_REQUEST_SEEN";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        if(shouldShowReviewRequestDialog()) {
            showReviewRequestDialog();
        }

        verifyDataAvailable();
        setupNavigationDrawer();
        setupToolbar();
        setupClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_character_select, menu);
        MenuItem versionItem = menu.findItem(R.id.version_item);
        String version = String.format(getString(R.string.version_format), BuildConfig.VERSION_NAME);
        versionItem.setTitle(version);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                FeedbackUtil.sendFeedback(this);
                return true;
            default:
                Log.d("findme", "clicked navigation");
                return drawerToggle.onOptionsItemSelected(item);
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

    private void setupNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
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

            if (shouldAnimateTransition()) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CharacterSelectActivity.this,
                        getImageViewForCharacter(view, clickedCharacter),
                        getString(R.string.character_select_transition));

                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    private boolean shouldAnimateTransition() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }

        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if (rotation != Surface.ROTATION_0 && rotation != Surface.ROTATION_180) {
            return false;
        }

        return true;
    }


    private View getImageViewForCharacter(View view, CharacterID characterID) {
        switch (characterID) {
            case RYU:
                return view.findViewById(R.id.ryu_image_view);
            case CHUN:
                return view.findViewById(R.id.chun_image_view);
            case DICTATOR:
                return view.findViewById(R.id.dictator_image_view);
            case BIRDIE:
                return view.findViewById(R.id.birdie_image_view);
            case NASH:
                return view.findViewById(R.id.nash_image_view);
            case CAMMY:
                return view.findViewById(R.id.cammy_image_view);
            case CLAW:
                return view.findViewById(R.id.claw_image_view);
            case LAURA:
                return view.findViewById(R.id.laura_image_view);
            case KEN:
                return view.findViewById(R.id.ken_image_view);
            case NECALLI:
                return view.findViewById(R.id.necalli_image_view);
            case RASHID:
                return view.findViewById(R.id.rashid_image_view);
            case MIKA:
                return view.findViewById(R.id.mika_image_view);
            case ZANGIEF:
                return view.findViewById(R.id.zangief_image_view);
            case FANG:
                return view.findViewById(R.id.fang_image_view);
            case DHALSIM:
                return view.findViewById(R.id.dhalsim_image_view);
            case KARIN:
                return view.findViewById(R.id.karin_image_view);
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
