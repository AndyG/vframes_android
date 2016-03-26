package com.angarron.vframes.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.StreamsRecyclerViewAdapter;
import com.angarron.vframes.data.twitch.TwitchJsonParser;
import com.angarron.vframes.data.twitch.TwitchStream;
import com.angarron.vframes.network.TwitchRESTApi;
import com.angarron.vframes.util.FeedbackUtil;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentStreamsActivity extends NavigationHostActivity implements StreamsRecyclerViewAdapter.IStreamClickListener {

    private static final String STREET_FIGHTER_SEARCH_STRING = "Street Fighter V";

    ProgressBar progressBar;
    RecyclerView streamsRecyclerView;
    View failedToLoadContent;
    View noStreamsLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_streamers);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        streamsRecyclerView = (RecyclerView) findViewById(R.id.streams_recycler_view);
        streamsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        failedToLoadContent = findViewById(R.id.failed_to_load_layout);
        noStreamsLayout = findViewById(R.id.no_streams_layout);

        setupToolbar();
        loadStreams();
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
                loadStreams();
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
            actionBar.setTitle("Current Live Streams");
        }
    }

    public void loadStreams() {
        showProgressBar();

        streamsRecyclerView.setAdapter(null);

        TwitchRESTApi twitchRESTApi = createTwitchApi();
        Call<JsonObject> call = twitchRESTApi.searchForGame(STREET_FIGHTER_SEARCH_STRING);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    TwitchJsonParser twitchJsonParser = new TwitchJsonParser();
                    List<TwitchStream> twitchStreams = twitchJsonParser.parse(response.body());
                    if (!twitchStreams.isEmpty()) {
                        streamsRecyclerView.setAdapter(
                                new StreamsRecyclerViewAdapter(twitchStreams,
                                        CurrentStreamsActivity.this,
                                        CurrentStreamsActivity.this));
                        showRecyclerView();
                    } else {
                        showNoStreamsView();
                    }
                } else {
                    showFailureUI();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                showFailureUI();
            }
        });
    }

    private void showNoStreamsView() {
        failedToLoadContent.setVisibility(View.INVISIBLE);
        streamsRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        noStreamsLayout.setVisibility(View.VISIBLE);
    }

    private void showFailureUI() {
        streamsRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        noStreamsLayout.setVisibility(View.INVISIBLE);

        failedToLoadContent.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        streamsRecyclerView.setVisibility(View.INVISIBLE);
        noStreamsLayout.setVisibility(View.INVISIBLE);
        failedToLoadContent.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);
    }

    private void showRecyclerView() {
        progressBar.setVisibility(View.INVISIBLE);
        noStreamsLayout.setVisibility(View.INVISIBLE);
        failedToLoadContent.setVisibility(View.INVISIBLE);

        streamsRecyclerView.setVisibility(View.VISIBLE);
    }

    private TwitchRESTApi createTwitchApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitch.tv/kraken/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TwitchRESTApi.class);
    }

    @Override
    public void onStreamClicked(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
