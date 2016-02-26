package com.angarron.vframes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.R;
import com.angarron.vframes.adapter.NavigationRecyclerViewAdapter;

public class NavigationHostActivity extends AppCompatActivity implements NavigationRecyclerViewAdapter.IMenuClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupNavigationDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        RecyclerView drawerRecycler = (RecyclerView) findViewById(R.id.drawer_recyclerview);
        drawerRecycler.setLayoutManager(new LinearLayoutManager(this));
        drawerRecycler.setAdapter(new NavigationRecyclerViewAdapter(this, this));

        TextView versionText = (TextView) drawerLayout.findViewById(R.id.version_textview);
        versionText.setText(String.format(getString(R.string.version_format), BuildConfig.VERSION_NAME));

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer){

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

    @Override
    public void onCharacterDataClicked() {
        drawerLayout.closeDrawer(Gravity.LEFT);

        if (!(this instanceof CharacterSelectActivity)) {
            Intent launchHomeScreenIntent = new Intent(this, CharacterSelectActivity.class);
            launchHomeScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(launchHomeScreenIntent);
            finish();
        }
    }

    @Override
    public void onStreamersClicked() {
        drawerLayout.closeDrawer(Gravity.LEFT);

        if (!(this instanceof CurrentStreamsActivity)) {
            Intent launchStreamsActivityIntent = new Intent(this, CurrentStreamsActivity.class);
            launchStreamsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(launchStreamsActivityIntent);
            finish();
        }
    }
}
