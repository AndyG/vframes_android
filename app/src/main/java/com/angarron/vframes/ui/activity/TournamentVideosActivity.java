package com.angarron.vframes.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.angarron.vframes.R;
import com.angarron.vframes.ui.fragment.TournamentVideosFragment;
import com.angarron.vframes.util.FeedbackUtil;

public class TournamentVideosActivity extends NavigationHostActivity {

    private static final String TAG_TOURNAMENT_VIDEOS_FRAGMENT = "TAG_TOURNAMENT_VIDEOS_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_activity);
        setupToolbar();

        //Add the fragment if it is the first launch of this activity.
        if (savedInstanceState == null) {
            TournamentVideosFragment tournamentVideosFragment = new TournamentVideosFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.videos_fragment_container, tournamentVideosFragment, TAG_TOURNAMENT_VIDEOS_FRAGMENT).commit();
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
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_TOURNAMENT_VIDEOS_FRAGMENT);
                if (fragment != null && fragment instanceof TournamentVideosFragment) {
                    ((TournamentVideosFragment) fragment).refreshVideos();
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
            actionBar.setTitle("Recent Tournament Matches");
        }
    }
}
