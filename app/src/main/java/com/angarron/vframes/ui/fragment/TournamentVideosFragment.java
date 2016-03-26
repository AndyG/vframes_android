package com.angarron.vframes.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.YoutubeVideosRecyclerAdapter;
import com.angarron.vframes.data.videos.ITournamentVideo;
import com.angarron.vframes.data.videos.VideosJsonParser;
import com.angarron.vframes.data.videos.YoutubeVideo;
import com.angarron.vframes.network.VFramesRESTApi;
import com.angarron.vframes.network.YoutubeVideosLoader;
import com.angarron.vframes.util.CharacterResourceUtil;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.model.CharacterID;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TournamentVideosFragment extends Fragment implements YoutubeVideosRecyclerAdapter.IVideoSelectedListener, AdapterView.OnItemSelectedListener {

    public static final String CHARACTER_ID = "CHARACTER_ID";

    private CharacterID firstCharacter;
    private CharacterID matchupFilterCharacter;

    RecyclerView videosRecyclerView;
    Spinner spinner;
    View videosContainer;
    View failedToLoadLayout;
    TextView noVideosLayout;
    ProgressBar progressBar;
    List<String> spinnerItems;
    List<ITournamentVideo> tournamentVideos;

    @Override
    public void onVideoSelected(String videoUrl) {
        Activity hostActivity = getActivity();
        ITournamentVideosFragmentHost host = (ITournamentVideosFragmentHost) hostActivity;
        host.onVideoSelected(videoUrl);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity hostActivity = getActivity();

        firstCharacter = getCharacterIdFromArguments();

        View view = inflater.inflate(R.layout.fragment_tournament_videos, container, false);

        //If the fragment is not targeted at a particular character, we don't need to
        //show the matchup filter.
        View matchupFilterContainer = view.findViewById(R.id.matchup_filter_container);
        if (firstCharacter == null) {
            matchupFilterContainer.setVisibility(View.GONE);
        } else {
            matchupFilterContainer.setVisibility(View.VISIBLE);
        }

        videosContainer = view.findViewById(R.id.videos_container);
        videosRecyclerView = (RecyclerView) view.findViewById(R.id.videos_recycler_view);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        noVideosLayout = (TextView) view.findViewById(R.id.no_videos_layout);
        failedToLoadLayout = view.findViewById(R.id.failed_to_load_layout);
        spinner = (Spinner) view.findViewById(R.id.filter_matchup_spinner);
        setupSpinner();
        loadTournamentVideos();
        return view;
    }

    private void setupSpinner() {
        initializeSpinnerItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void loadTournamentVideos() {
        showProgressBar();
        videosRecyclerView.setAdapter(null);

        VFramesRESTApi restApi = createVFramesApi();
        Call<JsonArray> call;

        if (firstCharacter != null) {
            call = restApi.getTournamentVideosForCharacter(firstCharacter.toString());
        } else {
            call = restApi.getAllTournamentVideos();
        }

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    processSuccessfulResponse(response.body());
                } else {
                    showFailureUI();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                showFailureUI();
            }
        });
    }

    private void processSuccessfulResponse(JsonArray body) {
        tournamentVideos = VideosJsonParser.parseTournamentVideos(body);
        if (!tournamentVideos.isEmpty()) {
            Log.d("findme", "tournamentVideosSize: " + tournamentVideos.size());
            loadVideos();
        } else {
            Log.d("findme", "tournamentVideosSize: " + tournamentVideos.size());
            showNoVideosView();
        }
    }

    private void downloadYoutubeVideos(List<ITournamentVideo> tournamentVideos) {
        showProgressBar();
        YoutubeVideosLoader youtubeVideosLoader = new YoutubeVideosLoader(new LoadVideosListener());
        youtubeVideosLoader.loadVideos(tournamentVideos);
    }

    private VFramesRESTApi createVFramesApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://still-hollows-20653.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(VFramesRESTApi.class);
    }

    private void showNoVideosView() {
        failedToLoadLayout.setVisibility(View.INVISIBLE);
        videosContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        String noVideosText;
        Context context = getContext();
        if (firstCharacter != null && context != null) {
            noVideosText = getString(R.string.videos_not_available, CharacterResourceUtil.getCharacterDisplayName(context, firstCharacter));
        } else {
            noVideosText = getString(R.string.tournament_videos_not_available);
        }
        noVideosLayout.setText(noVideosText);
        noVideosLayout.setVisibility(View.VISIBLE);
    }

    private void showFailureUI() {
        videosContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        noVideosLayout.setVisibility(View.INVISIBLE);

        failedToLoadLayout.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        videosContainer.setVisibility(View.INVISIBLE);
        noVideosLayout.setVisibility(View.INVISIBLE);
        failedToLoadLayout.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);
    }

    private void showRecyclerView() {
        progressBar.setVisibility(View.INVISIBLE);
        noVideosLayout.setVisibility(View.INVISIBLE);
        failedToLoadLayout.setVisibility(View.INVISIBLE);

        videosContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            matchupFilterCharacter = null;
        } else {
            matchupFilterCharacter = getSortedCharacters().get(position - 1);
        }

        if (tournamentVideos != null && !tournamentVideos.isEmpty()) {
            loadVideos();
        }
    }

    private void loadVideos() {
        List<ITournamentVideo> filteredVideos = new ArrayList<>();
        if (matchupFilterCharacter != null) {
            Log.d("findme", "filtering on character: " + matchupFilterCharacter.toString());
            for (ITournamentVideo tournamentVideo : tournamentVideos) {
                if (tournamentVideo.getFirstCharacter() == firstCharacter && tournamentVideo.getSecondCharacter() == matchupFilterCharacter) {
                    filteredVideos.add(tournamentVideo);
                } else if (tournamentVideo.getSecondCharacter() == firstCharacter && tournamentVideo.getFirstCharacter() == matchupFilterCharacter) {
                    filteredVideos.add(tournamentVideo);
                }
            }
        } else {
            //If there is no filter, just use all the videos
            filteredVideos = tournamentVideos;
        }
        downloadYoutubeVideos(filteredVideos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //what does this do?!?!
    }

    public void initializeSpinnerItems() {
        List<CharacterID> sortedCharacters = getSortedCharacters();
        List<String> sortedCharacterNames = new ArrayList<>();
        for (CharacterID characterID : sortedCharacters) {
            sortedCharacterNames.add(CharacterResourceUtil.getCharacterDisplayName(getContext(), characterID));
        }
        spinnerItems = new ArrayList<>();
        spinnerItems.add("No Filter");
        spinnerItems.addAll(sortedCharacterNames);
    }

    private List<CharacterID> getSortedCharacters() {
        List<CharacterID> characterIDs = Arrays.asList(CharacterID.values());
        Collections.sort(characterIDs, characterIDComparator);
        return characterIDs;
    }

    public void refreshVideos() {
        loadTournamentVideos();
    }

    public interface ITournamentVideosFragmentHost {
        void onVideoSelected(String videoUrl);
    }

    private class LoadVideosListener implements YoutubeVideosLoader.Listener {
        @Override
        public void onVideosLoaded(List<YoutubeVideo> youtubeVideos) {
            videosRecyclerView.setAdapter(new YoutubeVideosRecyclerAdapter(youtubeVideos, TournamentVideosFragment.this));
            showRecyclerView();
            if (youtubeVideos.isEmpty() && matchupFilterCharacter != null) {
                Context context = getContext();
                if (context != null) {
                    Toast.makeText(context, R.string.filtered_videos_not_available, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure() {
            showFailureUI();
        }
    }

    private CharacterID getCharacterIdFromArguments() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CHARACTER_ID)) {
            return (CharacterID) bundle.getSerializable(CHARACTER_ID);
        } else {
            return null;
        }
    }

    private Comparator<CharacterID> characterIDComparator = new Comparator<CharacterID>() {
        @Override
        public int compare(CharacterID lhs, CharacterID rhs) {
            String lhsCharacterDisplayName = CharacterResourceUtil.getCharacterDisplayName(getContext(), lhs);
            String rhsCharacterDisplayName = CharacterResourceUtil.getCharacterDisplayName(getContext(), rhs);
            return lhsCharacterDisplayName.compareTo(rhsCharacterDisplayName);
        }
    };
}
