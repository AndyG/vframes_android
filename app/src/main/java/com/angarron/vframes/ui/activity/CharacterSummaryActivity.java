package com.angarron.vframes.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.R;
import com.angarron.vframes.adapter.SummaryPagerAdapter;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.ui.fragment.BreadAndButterFragment;
import com.angarron.vframes.ui.fragment.FrameDataFragment;
import com.angarron.vframes.ui.fragment.MoveListFragment;
import com.angarron.vframes.util.FeedbackUtil;
import com.crashlytics.android.Crashlytics;

import java.util.List;
import java.util.Map;

import data.model.CharacterID;
import data.model.IDataModel;
import data.model.character.FrameData;
import data.model.character.SFCharacter;
import data.model.character.bnb.BreadAndButterModel;
import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

public class CharacterSummaryActivity extends AppCompatActivity implements
        MoveListFragment.IMoveListFragmentHost,
        FrameDataFragment.IFrameDataFragmentHost,
        BreadAndButterFragment.IBreadAndButterFragmentHost, AdapterView.OnItemSelectedListener, ViewPager.OnPageChangeListener {

    public static final String INTENT_EXTRA_TARGET_CHARACTER = "INTENT_EXTRA_TARGET_CHARACTER";
    private static final String ALTERNATE_FRAME_DATA_SELECTED = "ALTERNATE_FRAME_DATA_SELECTED";

    private CharacterID targetCharacter;
    private boolean alternateFrameDataSelected = false;

    private ViewPager viewPager;
    private Spinner spinner;
    private MenuItem alternateFrameDataItem;

    private FrameDataFragment frameDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_summary);

        //Postpone the transition to give the header image time to get laid out.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        try {
            targetCharacter = (CharacterID) getIntent().getSerializableExtra(INTENT_EXTRA_TARGET_CHARACTER);
        } catch (ClassCastException e) {
            Crashlytics.log(Log.ERROR, VFramesApplication.APP_LOGGING_TAG, "failed to parse intent in CharacterSummaryActivity");
            finish();
        }

        //Verify the data is still available. If not, send to splash screen.
        if (dataIsAvailable()) {

            if (savedInstanceState != null && savedInstanceState.containsKey(ALTERNATE_FRAME_DATA_SELECTED)) {
                alternateFrameDataSelected = savedInstanceState.getBoolean(ALTERNATE_FRAME_DATA_SELECTED);
            }

            //Load the toolbar based on the target character
            setupToolbar();
            setCharacterDetails();
            setupViewPager();
            setupSpinner();
        } else {
            sendToSplashScreen();
        }
    }

    private void setupSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (dataIsAvailable()) {
            alternateFrameDataItem = menu.findItem(R.id.action_alternate_frame_data_toggle);
            setAlternateFrameDataMenuState();
            return super.onPrepareOptionsMenu(menu);
        } else {
            sendToSplashScreen();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_summary, menu);
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
            case R.id.action_alternate_frame_data_toggle:
                switch (targetCharacter) {
                    case MIKA:
                    case DHALSIM:
                    case RASHID:
                    case NASH:
                        throw new RuntimeException("toggled vtrigger for invalid character");
                    default:
                        toggleFrameData();
                        return true;
                }
            default:
                throw new RuntimeException("invalid menu item clicked");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ALTERNATE_FRAME_DATA_SELECTED, alternateFrameDataSelected);
    }

    //Move List Fragment Host
    @Override
    public Map<MoveCategory, List<IMoveListEntry>> getMoveList() {
        VFramesApplication application = (VFramesApplication) getApplication();
        IDataModel dataModel = application.getDataModel();
        SFCharacter targetCharacterModel = dataModel.getCharactersModel().getCharacter(targetCharacter);
        return targetCharacterModel.getMoveList();
    }

    //Frame Data Fragment Host
    @Override
    public void registerFrameDataFragment(FrameDataFragment frameDataFragment) {
        this.frameDataFragment = frameDataFragment;
    }

    @Override
    public void unregisterFrameDataFragment() {
        frameDataFragment = null;
    }

    @Override
    public FrameData getFrameData() {
        VFramesApplication application = (VFramesApplication) getApplication();
        IDataModel dataModel = application.getDataModel();
        SFCharacter targetCharacterModel = dataModel.getCharactersModel().getCharacter(targetCharacter);
        return targetCharacterModel.getFrameData();
    }

    //BnB Fragment Host
    @Override
    public BreadAndButterModel getBreadAndButterModel() {
        VFramesApplication application = (VFramesApplication) getApplication();
        IDataModel dataModel = application.getDataModel();
        SFCharacter targetCharacterModel = dataModel.getCharactersModel().getCharacter(targetCharacter);
        return targetCharacterModel.getBreadAndButters();
    }

    @Override
    public String getCharacterDisplayName() {
        return getString(getNameResource());
    }

    @Override
    public ColorDrawable getTargetCharacterColor() {
        return getCharacterPrimaryColorDrawable();
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    private boolean dataIsAvailable() {
        VFramesApplication application = (VFramesApplication) getApplication();
        return (application.getDataModel() != null);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new SummaryPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        pagerTabStrip.setBackgroundColor(getCharacterAccentColor());
        pagerTabStrip.setTabIndicatorColorResource(R.color.tab_indicator_color);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.summary_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getCharacterAccentColor());
        }

        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            String toolbarTitleFormat = getString(R.string.summary_toolbar_title);
            String characterName = getString(getNameResource());
            actionBar.setTitle(String.format(toolbarTitleFormat, characterName));
            actionBar.setBackgroundDrawable(getCharacterPrimaryColorDrawable());

            if (viewExists(R.id.summary_character_image)) {
                final ImageView summaryCharacterImage = (ImageView) findViewById(R.id.summary_character_image);
                ViewTreeObserver viewTreeObserver = summaryCharacterImage.getViewTreeObserver();
                viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        summaryCharacterImage.getViewTreeObserver().removeOnPreDrawListener(this);
                        finishEnterTransition();
                        return true;
                    }
                });
                summaryCharacterImage.setImageResource(getCharacterBannerResource());
            } else {
                //Even though there is no header image, we still need to call startPostponedEnterTransition()
                //to finish transitioning to this activity.
                finishEnterTransition();
            }
        }
    }

    private void finishEnterTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startPostponedEnterTransition();
        }
    }

    private void toggleFrameData() {
        alternateFrameDataSelected = !alternateFrameDataSelected;
        showAlternateFrameDataToast();
        setAlternateFrameDataMenuState();
        //get reference to frame data fragment and update it with new frame data
        if (frameDataFragment != null) {
            frameDataFragment.setShowAlternateFrameData(alternateFrameDataSelected);
        }
    }

    private void showAlternateFrameDataToast() {
        int stringRes;
        if (targetCharacter != CharacterID.CLAW) {
            stringRes = alternateFrameDataSelected ? R.string.showing_trigger_data : R.string.showing_non_trigger_data;
        } else {
            stringRes = alternateFrameDataSelected ? R.string.showing_claw_off_data : R.string.showing_claw_on_data;
        }
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }

    private void setAlternateFrameDataMenuState() {
        VFramesApplication application = (VFramesApplication) getApplication();
        IDataModel dataModel = application.getDataModel();
        SFCharacter targetCharacterModel = dataModel.getCharactersModel().getCharacter(targetCharacter);
        FrameData characterFrameData = targetCharacterModel.getFrameData();

        if (characterFrameData != null && characterFrameData.hasAlternateFrameData()) {
            alternateFrameDataItem.setIcon(resolveAlternateFrameDataMenuDrawable());
        } else {
            alternateFrameDataItem.setVisible(false);
        }
    }

    private int resolveAlternateFrameDataMenuDrawable() {
        if(targetCharacter == CharacterID.CLAW) {
            return alternateFrameDataSelected ? R.drawable.claw_off : R.drawable.claw_on;
        } else {
            return alternateFrameDataSelected ? R.drawable.fire_logo : R.drawable.logo;
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

    private boolean viewExists(int viewId) {
        return findViewById(viewId) != null;
    }

    private int getCharacterBannerResource() {
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
                throw new RuntimeException("unable to resolve character drawable: " + targetCharacter);
        }
    }

    private ColorDrawable getCharacterPrimaryColorDrawable() {
        switch(targetCharacter) {
            case RYU:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.ryu_primary));
            case CHUN:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.chun_primary));
            case DICTATOR:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.dictator_primary));
            case BIRDIE:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.birdie_primary));
            case NASH:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.nash_primary));
            case CAMMY:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.cammy_primary));
            case KEN:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.ken_primary));
            case MIKA:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.mika_primary));
            case NECALLI:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.necalli_primary));
            case CLAW:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.claw_primary));
            case RASHID:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.rashid_primary));
            case KARIN:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.karin_primary));
            case LAURA:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.laura_primary));
            case DHALSIM:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.dhalsim_primary));
            case ZANGIEF:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.zangief_primary));
            case FANG:
                return new ColorDrawable(ContextCompat.getColor(this, R.color.fang_primary));
            default:
                throw new RuntimeException("unable to resolve character accent color drawable: " + targetCharacter);
        }
    }


    private int getCharacterAccentColor() {
        switch(targetCharacter) {
            case RYU:
                return ContextCompat.getColor(this, R.color.ryu_accent);
            case CHUN:
                return ContextCompat.getColor(this, R.color.chun_accent);
            case DICTATOR:
                return ContextCompat.getColor(this, R.color.dictator_accent);
            case BIRDIE:
                return ContextCompat.getColor(this, R.color.birdie_accent);
            case NASH:
                return ContextCompat.getColor(this, R.color.nash_accent);
            case CAMMY:
                return ContextCompat.getColor(this, R.color.cammy_accent);
            case KEN:
                return ContextCompat.getColor(this, R.color.ken_accent);
            case MIKA:
                return ContextCompat.getColor(this, R.color.mika_accent);
            case NECALLI:
                return ContextCompat.getColor(this, R.color.necalli_accent);
            case CLAW:
                return ContextCompat.getColor(this, R.color.claw_accent);
            case RASHID:
                return ContextCompat.getColor(this, R.color.rashid_accent);
            case KARIN:
                return ContextCompat.getColor(this, R.color.karin_accent);
            case LAURA:
                return ContextCompat.getColor(this, R.color.laura_accent);
            case DHALSIM:
                return ContextCompat.getColor(this, R.color.dhalsim_accent);
            case ZANGIEF:
                return ContextCompat.getColor(this, R.color.zangief_accent);
            case FANG:
                return ContextCompat.getColor(this, R.color.fang_accent);
            default:
                throw new RuntimeException("unable to resolve character accent color drawable: " + targetCharacter);
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
                throw new RuntimeException("unable to resolve character name: " + targetCharacter);
        }
    }

    private void setCharacterDetails() {
        int titleStringId;
        int styleStringId;
        int healthStringId;
        int stunStringId;

        switch (targetCharacter) {
            case RYU:
                titleStringId = R.string.ryu_title;
                styleStringId = R.string.ryu_style;
                healthStringId = R.string.ryu_health;
                stunStringId = R.string.ryu_stun;
                break;
            case CHUN:
                titleStringId = R.string.chun_title;
                styleStringId = R.string.chun_style;
                healthStringId = R.string.chun_health;
                stunStringId = R.string.chun_stun;
                break;
            case DICTATOR:
                titleStringId = R.string.dictator_title;
                styleStringId = R.string.dictator_style;
                healthStringId = R.string.dictator_health;
                stunStringId = R.string.dictator_stun;
                break;
            case BIRDIE:
                titleStringId = R.string.birdie_title;
                styleStringId = R.string.birdie_style;
                healthStringId = R.string.birdie_health;
                stunStringId = R.string.birdie_stun;
                break;
            case NASH:
                titleStringId = R.string.nash_title;
                styleStringId = R.string.nash_style;
                healthStringId = R.string.nash_health;
                stunStringId = R.string.nash_stun;
                break;
            case CAMMY:
                titleStringId = R.string.cammy_title;
                styleStringId = R.string.cammy_style;
                healthStringId = R.string.cammy_health;
                stunStringId = R.string.cammy_stun;
                break;
            case CLAW:
                titleStringId = R.string.claw_title;
                styleStringId = R.string.claw_style;
                healthStringId = R.string.claw_health;
                stunStringId = R.string.claw_stun;
                break;
            case LAURA:
                titleStringId = R.string.laura_title;
                styleStringId = R.string.laura_style;
                healthStringId = R.string.laura_health;
                stunStringId = R.string.laura_stun;
                break;
            case KEN:
                titleStringId = R.string.ken_title;
                styleStringId = R.string.ken_style;
                healthStringId = R.string.ken_health;
                stunStringId = R.string.ken_stun;
                break;
            case NECALLI:
                titleStringId = R.string.necalli_title;
                styleStringId = R.string.necalli_style;
                healthStringId = R.string.necalli_health;
                stunStringId = R.string.necalli_stun;
                break;
            case RASHID:
                titleStringId = R.string.rashid_title;
                styleStringId = R.string.rashid_style;
                healthStringId = R.string.rashid_health;
                stunStringId = R.string.rashid_stun;
                break;
            case MIKA:
                titleStringId = R.string.mika_title;
                styleStringId = R.string.mika_style;
                healthStringId = R.string.mika_health;
                stunStringId = R.string.mika_stun;
                break;
            case ZANGIEF:
                titleStringId = R.string.zangief_title;
                styleStringId = R.string.zangief_style;
                healthStringId = R.string.zangief_health;
                stunStringId = R.string.zangief_stun;
                break;
            case FANG:
                titleStringId = R.string.fang_title;
                styleStringId = R.string.fang_style;
                healthStringId = R.string.fang_health;
                stunStringId = R.string.fang_stun;
                break;
            case DHALSIM:
                titleStringId = R.string.dhalsim_title;
                styleStringId = R.string.dhalsim_style;
                healthStringId = R.string.dhalsim_health;
                stunStringId = R.string.dhalsim_stun;
                break;
            case KARIN:
                titleStringId = R.string.karin_title;
                styleStringId = R.string.karin_style;
                healthStringId = R.string.karin_health;
                stunStringId = R.string.karin_stun;
                break;
            default:
                throw new IllegalArgumentException("could not find character: " + targetCharacter.toString());
        }

        if (viewExists(R.id.banner_character_details)) {
            ((TextView) findViewById(R.id.banner_character_title)).setText(titleStringId);

            String healthText = String.format(getString(R.string.health_format), getString(healthStringId));
            ((TextView) findViewById(R.id.banner_character_health)).setText(healthText);

            String stunText = String.format(getString(R.string.stun_format), getString(stunStringId));
            ((TextView) findViewById(R.id.banner_character_stun)).setText(stunText);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        viewPager.setCurrentItem(i, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //no-op
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
}
