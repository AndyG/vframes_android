package com.angarron.vframes.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.YoutubeVideosRecyclerAdapter;
import com.angarron.vframes.data.videos.VideosJsonParser;
import com.angarron.vframes.data.videos.IGuideVideo;
import com.angarron.vframes.data.videos.YoutubeVideo;
import com.angarron.vframes.network.VFramesRESTApi;
import com.angarron.vframes.network.YoutubeVideosLoader;
import com.angarron.vframes.util.CharacterResourceUtil;
import com.google.gson.JsonArray;

import java.util.List;

import data.model.CharacterID;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuideVideosFragment extends Fragment implements YoutubeVideosRecyclerAdapter.IVideoSelectedListener {

    public static final String CHARACTER_ID = "CHARACTER_ID";

    private CharacterID characterID;

    RecyclerView videosRecyclerView;
    View failedToLoadLayout;
    TextView noVideosLayout;
    ProgressBar progressBar;

    @Override
    public void onVideoSelected(String videoUrl) {
        Activity hostActivity = getActivity();
        IGuideVideosFragmentHost host = (IGuideVideosFragmentHost) hostActivity;
        host.onVideoSelected(videoUrl);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity hostActivity = getActivity();

        characterID = getCharacterIdFromArguments();

        View view = inflater.inflate(R.layout.fragment_guide_videos, container, false);
        videosRecyclerView = (RecyclerView) view.findViewById(R.id.videos_recycler_view);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        noVideosLayout = (TextView) view.findViewById(R.id.no_videos_layout);
        failedToLoadLayout = view.findViewById(R.id.failed_to_load_layout);
        loadGuideVideos();
        return view;
    }

    private void loadGuideVideos() {
        showProgressBar();
        videosRecyclerView.setAdapter(null);

        VFramesRESTApi restApi = createVFramesApi();

        String characterQueryParameter;
        if (characterID == null) {
            //If no character is specified, we want the "general" guides (not character-specific).
            characterQueryParameter = "general";
        } else {
            characterQueryParameter = characterID.toString();
        }

        Call<JsonArray> call = restApi.getGuideVideosForCharacter(characterQueryParameter);
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
        List<IGuideVideo> guideVideos = VideosJsonParser.parseGuideVideos(body);
        if (!guideVideos.isEmpty()) {
            loadYoutubeVideosModel(guideVideos);
        } else {
            showNoVideosView();
        }
    }

    private void loadYoutubeVideosModel(List<IGuideVideo> guideVideos) {
        YoutubeVideosLoader youtubeVideosLoader = new YoutubeVideosLoader(new LoadVideosListener());
        youtubeVideosLoader.loadVideos(guideVideos);
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
            String noVideosText;
            if (characterID != null) {
                noVideosText = hostActivity.getString(R.string.videos_not_available, CharacterResourceUtil.getCharacterDisplayName(hostActivity, characterID));
            } else {
                noVideosText = hostActivity.getString(R.string.guides_not_available);
            }
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

    public void refreshVideos() {
        loadGuideVideos();
    }

    public interface IGuideVideosFragmentHost {
        void onVideoSelected(String videoUrl);
    }

    private class LoadVideosListener implements YoutubeVideosLoader.Listener {
        @Override
        public void onVideosLoaded(List<YoutubeVideo> youtubeVideos) {
            videosRecyclerView.setAdapter(new YoutubeVideosRecyclerAdapter(youtubeVideos, GuideVideosFragment.this));
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
            return null;
        }
    }
}
