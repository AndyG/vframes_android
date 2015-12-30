package com.angarron.vframes.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.SummaryPagerAdapter;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.ui.fragment.MoveListFragment;
import com.angarron.vframes.util.FeedbackUtil;

import java.util.List;
import java.util.Map;

import data.model.CharacterID;
import data.model.IDataModel;
import data.model.character.SFCharacter;
import data.model.move.IMoveListMove;
import data.model.move.MoveCategory;

//This activity will house a ViewSwitcher which will have
//move list and frame data for the selected character.
public class CharacterSummaryActivity extends AppCompatActivity implements MoveListFragment.IMoveListFragmentHost {

    public static final String INTENT_EXTRA_TARGET_CHARACTER = "INTENT_EXTRA_TARGET_CHARACTER";

    private CharacterID targetCharacter;

    private ViewPager viewPager;
    private PagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_summary);

        try {
            targetCharacter = (CharacterID) getIntent().getSerializableExtra(INTENT_EXTRA_TARGET_CHARACTER);
        } catch (ClassCastException e) {
            Log.e(VFramesApplication.APP_LOGGING_TAG, "failed to parse intent", e);
            finish();
        }

        //Verify the data is still available. If not, send to splash screen.
        verifyDataAvailable();

        //Load the toolbar based on the target character
        setupToolbar();
        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_feedback:
                FeedbackUtil.sendFeedback(this);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                Log.e(VFramesApplication.APP_LOGGING_TAG, "invalid menu item clicked: " + item.getItemId());
                return false;
        }
    }

    @Override
    public Map<MoveCategory, List<IMoveListMove>> getMoveList() {
        VFramesApplication application = (VFramesApplication) getApplication();
        IDataModel dataModel = application.getDataModel();
        SFCharacter targetCharacterModel = dataModel.getCharactersModel().getCharacter(targetCharacter);
        return targetCharacterModel.getMoveList();
    }

    private void verifyDataAvailable() {
        VFramesApplication application = (VFramesApplication) getApplication();
        if (application.getDataModel() == null) {
            Intent startSplashIntent = new Intent(this, SplashActivity.class);
            startActivity(startSplashIntent);
            finish();
        }
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new SummaryPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.tab_indicator_color));
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.summary_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            String toolbarTitleFormat = getString(R.string.summary_toolbar_title);
            String characterName = getString(getNameResource());
            actionBar.setTitle(String.format(toolbarTitleFormat, characterName));
            actionBar.setBackgroundDrawable(getCharacterAccentColorDrawable());

            if (viewExists(R.id.summary_character_image)) {
                final ImageView summaryCharacterImage = (ImageView) findViewById(R.id.summary_character_image);
                ViewTreeObserver vto = summaryCharacterImage.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        Log.d("findme", "pixels WxH: " + summaryCharacterImage.getMeasuredWidth() + "x" + summaryCharacterImage.getMeasuredHeight());
                        return true;
                    }
                });
                summaryCharacterImage.setImageResource(getCharacterBannerResource());
            }
        }
    }

    private boolean viewExists(int viewId) {
        return findViewById(viewId) != null;
    }

    private int getCharacterBannerResource() {
        switch(targetCharacter) {
            case RYU:
                return R.drawable.ryu_banner;
            case CHUN:
                return R.drawable.chun_banner;
            case DICTATOR:
                return R.drawable.dictator_banner;
            case BIRDIE:
                return R.drawable.birdie_banner;
            case NASH:
                return R.drawable.nash_banner;
            case CAMMY:
                return R.drawable.cammy_banner;
            case KEN:
                return R.drawable.ken_banner;
            case MIKA:
                return R.drawable.mika_banner;
            case NECALLI:
                return R.drawable.necalli_banner;
            case CLAW:
                return R.drawable.claw_banner;
            case RASHID:
                return R.drawable.rashid_banner;
            case KARIN:
                return R.drawable.karin_banner;
            case LAURA:
                return R.drawable.laura_banner;
            case DHALSIM:
                return R.drawable.dhalsim_banner;
            case ZANGIEF:
                return R.drawable.zangief_banner;
            case FANG:
                return R.drawable.fang_banner;
            default:
                throw new RuntimeException("unable to resolve character drawable: " + targetCharacter);
        }
    }

    private int getTranslucentCharacterAccentColor() {
        switch(targetCharacter) {
            case RYU:
                return R.color.ryu_accent_translucent;
            case CHUN:
                return R.color.chun_accent_translucent;
            case DICTATOR:
                return R.color.dictator_accent_translucent;
            case BIRDIE:
                return R.color.birdie_accent_translucent;
            case NASH:
                return R.color.nash_accent_translucent;
            case CAMMY:
                return R.color.cammy_accent_translucent;
            case KEN:
                return R.color.ken_accent_translucent;
            case MIKA:
                return R.color.mika_accent_translucent;
            case NECALLI:
                return R.color.necalli_accent_translucent;
            case CLAW:
                return R.color.claw_accent_translucent;
            case RASHID:
                return R.color.rashid_accent_translucent;
            case KARIN:
                return R.color.karin_accent_translucent;
            case LAURA:
                return R.color.laura_accent_translucent;
            case DHALSIM:
                return R.color.dhalsim_accent_translucent;
            case ZANGIEF:
                return R.color.zangief_accent_translucent;
            case FANG:
                return R.color.fang_accent_translucent;
            default:
                throw new RuntimeException("unable to resolve character accent color: " + targetCharacter);
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
            default:
                throw new RuntimeException("unable to resolve character accent color: " + targetCharacter);
        }
    }

    private ColorDrawable getCharacterAccentColorDrawable() {
        switch(targetCharacter) {
            case RYU:
                return new ColorDrawable(getResources().getColor(R.color.ryu_accent));
            case CHUN:
                return new ColorDrawable(getResources().getColor(R.color.chun_accent));
            case DICTATOR:
                return new ColorDrawable(getResources().getColor(R.color.dictator_accent));
            case BIRDIE:
                return new ColorDrawable(getResources().getColor(R.color.birdie_accent));
            case NASH:
                return new ColorDrawable(getResources().getColor(R.color.nash_accent));
            case CAMMY:
                return new ColorDrawable(getResources().getColor(R.color.cammy_accent));
            case KEN:
                return new ColorDrawable(getResources().getColor(R.color.ken_accent));
            case MIKA:
                return new ColorDrawable(getResources().getColor(R.color.mika_accent));
            case NECALLI:
                return new ColorDrawable(getResources().getColor(R.color.necalli_accent));
            case CLAW:
                return new ColorDrawable(getResources().getColor(R.color.claw_accent));
            case RASHID:
                return new ColorDrawable(getResources().getColor(R.color.rashid_accent));
            case KARIN:
                return new ColorDrawable(getResources().getColor(R.color.karin_accent));
            case LAURA:
                return new ColorDrawable(getResources().getColor(R.color.laura_accent));
            case DHALSIM:
                return new ColorDrawable(getResources().getColor(R.color.dhalsim_accent));
            case ZANGIEF:
                return new ColorDrawable(getResources().getColor(R.color.zangief_accent));
            case FANG:
                return new ColorDrawable(getResources().getColor(R.color.fang_accent));
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
}
