package com.angarron.vframes.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.YoutubeVideosRecyclerAdapter;
import com.angarron.vframes.data.videos.RecommendedVideosModel;
import com.angarron.vframes.data.videos.YoutubeVideosModel;
import com.angarron.vframes.network.VFramesRESTApi;
import com.angarron.vframes.network.YoutubeVideosLoader;
import com.angarron.vframes.util.CharacterResourceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import data.model.CharacterID;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecommendedVideosFragment extends Fragment implements YoutubeVideosRecyclerAdapter.IVideoSelectedListener {

    public static final String CHARACTER_ID = "CHARACTER_ID";

    private CharacterID characterID;

    RecyclerView videosRecyclerView;
    View failedToLoadLayout;
    TextView noVideosLayout;
    ProgressBar progressBar;

    @Override
    public void onVideoSelected(String videoUrl) {
        Activity hostActivity = getActivity();
        IRecommendedVideosFragmentHost host = (IRecommendedVideosFragmentHost) hostActivity;
        host.onVideoSelected(videoUrl);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity hostActivity = getActivity();

        characterID = getCharacterIdFromArguments();

        View view = inflater.inflate(R.layout.fragment_recommended_videos, container, false);
        videosRecyclerView = (RecyclerView) view.findViewById(R.id.videos_recycler_view);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        noVideosLayout = (TextView) view.findViewById(R.id.no_videos_layout);
        failedToLoadLayout = view.findViewById(R.id.failed_to_load_layout);
        loadRecommendedVideos(characterID);
        return view;
    }

    private void loadRecommendedVideos(CharacterID characterId) {
        showProgressBar();
        videosRecyclerView.setAdapter(null);

        VFramesRESTApi restApi = createVFramesApi();
        Call<JsonObject> call = restApi.getGuideVideosForCharacter(characterId.toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    processSuccessfulResponse(response.body());
                } else {
                    Log.d("findme", response.message());
                    showFailureUI();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("findme", "call failed: " + call.request().toString() + " " + call.isExecuted());
                Log.d("findme", "got failure throwable", t);
                showFailureUI();
            }
        });
    }

    private void processSuccessfulResponse(JsonObject body) {
        Log.d("findme", "got videos: " + new Gson().toJson(body));

//        RecommendedVideosModel recommendedVideosModel = RecommendedVideosJsonParser.parseVideos(body.getAsJsonArray());
//        if (!recommendedVideosModel.isEmpty()) {
//            loadYoutubeVideosModel(recommendedVideosModel);
//        } else {
//            showNoVideosView();
//        }
    }

    private void loadYoutubeVideosModel(RecommendedVideosModel recommendedVideosModel) {
        YoutubeVideosLoader youtubeVideosLoader = new YoutubeVideosLoader(new LoadVideosListener());
        youtubeVideosLoader.loadVideos(recommendedVideosModel);
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
        videosRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        Activity hostActivity = getActivity();
        if (hostActivity != null) {
            String noVideosText = hostActivity.getString(R.string.videos_not_available, CharacterResourceUtil.getCharacterDisplayName(getActivity(), characterID));
            noVideosLayout.setText(noVideosText);
        }
        noVideosLayout.setVisibility(View.VISIBLE);
    }

    private void showFailureUI() {
        videosRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        noVideosLayout.setVisibility(View.INVISIBLE);

        failedToLoadLayout.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        videosRecyclerView.setVisibility(View.INVISIBLE);
        noVideosLayout.setVisibility(View.INVISIBLE);
        failedToLoadLayout.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);
    }

    private void showRecyclerView() {
        progressBar.setVisibility(View.INVISIBLE);
        noVideosLayout.setVisibility(View.INVISIBLE);
        failedToLoadLayout.setVisibility(View.INVISIBLE);

        videosRecyclerView.setVisibility(View.VISIBLE);
    }

    public interface IRecommendedVideosFragmentHost {
        void onVideoSelected(String videoUrl);
    }

    private class LoadVideosListener implements YoutubeVideosLoader.Listener {
        @Override
        public void onVideosLoaded(YoutubeVideosModel youtubeVideosModel) {
            videosRecyclerView.setAdapter(new YoutubeVideosRecyclerAdapter(youtubeVideosModel, RecommendedVideosFragment.this));
            showRecyclerView();
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
            throw new RuntimeException("no character id for FrameDataFragment");
        }
    }
}
