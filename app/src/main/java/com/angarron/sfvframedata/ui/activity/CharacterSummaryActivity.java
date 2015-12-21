package com.angarron.sfvframedata.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.angarron.sfvframedata.R;
import com.angarron.sfvframedata.adapter.IMoveListItem;
import com.angarron.sfvframedata.adapter.MovesRecyclerViewAdapter;
import com.angarron.sfvframedata.application.VFramesApplication;

import data.model.CharacterID;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.model.move.IDisplayableMove;

//This activity will house a ViewSwitcher which will have
//move list and frame data for the selected character.
public class CharacterSummaryActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_TARGET_CHARACTER = "INTENT_EXTRA_TARGET_CHARACTER";

    private CharacterID targetCharacter;

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

        //Load the toolbar based on the target character
        setupToolbar();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView movesRecyclerView = (RecyclerView) findViewById(R.id.moves_recycler_view);
        movesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<IMoveListItem> moveListItems = getMovesAsListItems();
        movesRecyclerView.setAdapter(new MovesRecyclerViewAdapter(getMovesAsListItems()));
    }

//    private List<IMoveListItem> getMovesAsListItems() {
    private List<String> getMovesAsListItems() {
        Map<String, List<IDisplayableMove>> moves = ((VFramesApplication) getApplication()).getDataModel().getCharactersModel().getCharacters().get(targetCharacter).getMoves();

//        List<IMoveListItem> moveListItems = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        for (String category : moves.keySet()) {
//            moveListItems.add(convertMoveToListItem(moveListItems));
            categories.add(category);
        }
        return categories;
    }

    private IMoveListItem convertMoveToListItem(List<IMoveListItem> moveListItems) {
        return null;
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
        }
    }
}
