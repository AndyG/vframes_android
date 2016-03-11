package com.angarron.vframes.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.R;
import com.angarron.vframes.adapter.ComparisonPagerAdapter;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.util.FeedbackUtil;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import data.model.CharacterID;

public class CharacterComparisonActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener, AdapterView.OnItemSelectedListener {

    public static final String INTENT_EXTRA_FIRST_CHARACTER = "INTENT_EXTRA_FIRST_CHARACTER";
    public static final String INTENT_EXTRA_SECOND_CHARACTER = "INTENT_EXTRA_SECOND_CHARACTER";

    private ViewPager viewPager;
    private Spinner spinner;

    private CharacterID firstCharacter;
    private CharacterID secondCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_comparison);

        //Postpone the transition to give the header image time to get laid out.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        try {
            firstCharacter = (CharacterID) getIntent().getSerializableExtra(INTENT_EXTRA_FIRST_CHARACTER);
            secondCharacter = (CharacterID) getIntent().getSerializableExtra(INTENT_EXTRA_SECOND_CHARACTER);
        } catch (ClassCastException e) {
            Crashlytics.log(Log.ERROR, VFramesApplication.APP_LOGGING_TAG, "failed to parse intent in CharacterComparisonActivity");
            finish();
        }

        //Verify the data is still available. If not, send to splash screen.
        if (dataIsAvailable()) {
            //Load the toolbar based on the target characters
            setupViewPager();
            setupToolbar();
            setupSpinner();
        } else {
            sendToSplashScreen();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_comparison, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_feedback:
                FeedbackUtil.sendFeedback(this);
                return true;
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                throw new RuntimeException("invalid menu item clicked");
        }
    }

    private void sendToSplashScreen() {
        //If this is a release build, log this issue to Crashlytics.
        if (!BuildConfig.DEBUG) {
            Crashlytics.logException(new Throwable("Sending user to splash screen because data was unavailable"));
        }

        Intent startSplashIntent = new Intent(this, SplashActivity.class);
        startActivity(startSplashIntent);
        finish();
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);

        PagerAdapter pagerAdapter = new ComparisonPagerAdapter(this, getSupportFragmentManager(), firstCharacter, secondCharacter);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.description_background));
        pagerTabStrip.setTabIndicatorColorResource(R.color.tab_indicator_color);
    }

    private void setupSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = createSpinnerArrayAdapter();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private ArrayAdapter<String> createSpinnerArrayAdapter() {
        List<String> spinnerArray =  new ArrayList<>();
        String firstCharacterName = getString(getNameResource(firstCharacter));
        String secondCharacterName = getString(getNameResource(secondCharacter));
        spinnerArray.add(getString(R.string.comparison_frame_data_format, firstCharacterName));
        spinnerArray.add(getString(R.string.comparison_frame_data_format, secondCharacterName));
        spinnerArray.add("Move Punisher");

        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.comparison_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            actionBar.setTitle(getTitleForCharacters());

            if (viewExists(R.id.first_character_image) && viewExists(R.id.second_character_image)) {
                setupTransitions();
                ImageView firstCharacterImage = (ImageView) findViewById(R.id.first_character_image);
                ImageView secondCharacterImage = (ImageView) findViewById(R.id.second_character_image);
                firstCharacterImage.setImageResource(getCharacterImage(firstCharacter));
                secondCharacterImage.setImageResource(getCharacterImage(secondCharacter));
            } else {
                //Even though there is no header image, we still need to call startPostponedEnterTransition()
                //to finish transitioning to this activity.
                finishEnterTransition();
            }
        }
    }

    private void setupTransitions() {

        final AtomicBoolean oneImageDrawn = new AtomicBoolean(false);

        final ImageView firstCharacterImage = (ImageView) findViewById(R.id.first_character_image);
        ViewTreeObserver firstCharacterImageViewTreeObserver = firstCharacterImage.getViewTreeObserver();
        firstCharacterImageViewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                firstCharacterImage.getViewTreeObserver().removeOnPreDrawListener(this);
                if (oneImageDrawn.get()) {
                    finishEnterTransition();
                } else {
                    oneImageDrawn.set(true);
                }
                return true;
            }
        });

        final ImageView secondCharacterImage = (ImageView) findViewById(R.id.second_character_image);
        ViewTreeObserver secondCharacterImageViewTreeObserver = secondCharacterImage.getViewTreeObserver();
        secondCharacterImageViewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                secondCharacterImage.getViewTreeObserver().removeOnPreDrawListener(this);
                if (oneImageDrawn.get()) {
                    finishEnterTransition();
                } else {
                    oneImageDrawn.set(true);
                }
                return true;
            }
        });
    }

    private int getCharacterImage(CharacterID characterID) {
        switch(characterID) {
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
            default:
                throw new RuntimeException("unable to resolve character drawable: " + characterID);
        }
    }

    private void finishEnterTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startPostponedEnterTransition();
        }
    }

    private boolean viewExists(int viewId) {
        return findViewById(viewId) != null;
    }

    private String getTitleForCharacters() {
        String firstCharacterName = getString(getNameResource(firstCharacter));
        String secondCharacterName = getString(getNameResource(secondCharacter));
        return getString(R.string.comparison_title_format, firstCharacterName, secondCharacterName);
    }

    private boolean dataIsAvailable() {
        VFramesApplication application = (VFramesApplication) getApplication();
        return (application.getDataModel() != null);
    }

    private int getNameResource(CharacterID characterID) {
        switch(characterID) {
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
            default:
                throw new RuntimeException("unable to resolve character name: " + characterID);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //no-op
    }

    @Override
    public void onPageSelected(int position) {
        spinner.setSelection(position, true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //no-op
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        viewPager.setCurrentItem(i, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //no-op
    }
}
