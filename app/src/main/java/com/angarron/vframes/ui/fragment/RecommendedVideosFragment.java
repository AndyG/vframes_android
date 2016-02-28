package com.angarron.vframes.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
import com.angarron.vframes.adapter.RecommendedVideosRecyclerViewAdapter;
import com.angarron.vframes.data.videos.RecommendedVideosJsonParser;
import com.angarron.vframes.data.videos.RecommendedVideosModel;
import com.angarron.vframes.network.YoutubeDataApi;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import data.model.CharacterID;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RecommendedVideosFragment extends Fragment implements RecommendedVideosRecyclerViewAdapter.IVideoSelectedListener {

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
        IRecommendedVideosFragmentHost host = (IRecommendedVideosFragmentHost) hostActivity;

        View view = inflater.inflate(R.layout.fragment_recommended_videos, container, false);
        videosRecyclerView = (RecyclerView) view.findViewById(R.id.videos_recycler_view);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        noVideosLayout = (TextView) view.findViewById(R.id.no_videos_layout);
        failedToLoadLayout = view.findViewById(R.id.failed_to_load_layout);
        loadRecommendedVideos(host.getTargetCharacterId());
        return view;
    }

    private void loadRecommendedVideos(CharacterID characterId) {
        showProgressBar();
        videosRecyclerView.setAdapter(null);
        JsonObject videosJson = loadBundledData();
        processSuccessfulResponse(videosJson);

//
//        VFramesRESTApi restApi = createRESTApi();
//        Call<JsonObject> call = restApi.getVideosForCharacter(characterId.toString());
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
//                if (response.isSuccess()) {
//                    processSuccessfulResponse(response.body());
//                } else {
//                    showFailureUI();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                showFailureUI();
//            }
//        });
    }

    private void processSuccessfulResponse(JsonObject body) {
        RecommendedVideosModel videosModel = RecommendedVideosJsonParser.parseVideos(body.getAsJsonArray("videos"));
        if (!videosModel.isEmpty()) {
            videosRecyclerView.setAdapter(new RecommendedVideosRecyclerViewAdapter(videosModel, createYoutubeApi(), this));
            showRecyclerView();
        } else {
            showNoVideosView();
        }
    }

    private YoutubeDataApi createYoutubeApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(YoutubeDataApi.class);
    }

    private void showNoVideosView() {
        failedToLoadLayout.setVisibility(View.INVISIBLE);
        videosRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        Activity hostActivity = getActivity();
        IRecommendedVideosFragmentHost host = (IRecommendedVideosFragmentHost) hostActivity;
        String noVideosText = hostActivity.getString(R.string.videos_not_available, host.getCharacterDisplayName());
        noVideosLayout.setText(noVideosText);
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

    private JsonObject loadBundledData() {
        Context context = getContext();
        Resources resources = getActivity().getResources();
        int identifier = resources.getIdentifier("test_videos", "raw", context.getPackageName());
        InputStream inputStream = resources.openRawResource(identifier);

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dataString = writer.toString();
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(dataString).getAsJsonObject();
    }

    public interface IRecommendedVideosFragmentHost {
        CharacterID getTargetCharacterId();
        String getCharacterDisplayName();
        void onVideoSelected(String videoUrl);
    }

}
