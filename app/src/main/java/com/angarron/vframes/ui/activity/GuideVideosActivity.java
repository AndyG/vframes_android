package com.angarron.vframes.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.angarron.vframes.R;
import com.angarron.vframes.ui.fragment.GuideVideosFragment;
import com.angarron.vframes.util.FeedbackUtil;

public class GuideVideosActivity extends NavigationHostActivity implements GuideVideosFragment.IGuideVideosFragmentHost {

    private static final String TAG_GUIDE_VIDEOS_FRAGMENT = "TAG_GUIDE_VIDEOS_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_activity);
        setupToolbar();

        //Add the fragment if it is the first launch of this activity.
        if (savedInstanceState == null) {
            GuideVideosFragment guideVideosFragment = new GuideVideosFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.videos_fragment_container, guideVideosFragment, TAG_GUIDE_VIDEOS_FRAGMENT).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_current_streams, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                FeedbackUtil.sendFeedback(this);
                return true;
            case R.id.action_refresh:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_GUIDE_VIDEOS_FRAGMENT);
                if (fragment != null && fragment instanceof GuideVideosFragment) {
                    ((GuideVideosFragment) fragment).refreshVideos();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Guide Videos");
        }
    }

    @Override
    public void onVideoSelected(String videoUrl) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(videoUrl));
        startActivity(i);
    }

}
