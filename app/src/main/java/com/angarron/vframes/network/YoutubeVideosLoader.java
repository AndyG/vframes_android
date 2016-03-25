package com.angarron.vframes.network;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.data.videos.RecommendedVideo;
import com.angarron.vframes.data.videos.RecommendedVideosModel;
import com.angarron.vframes.data.videos.YoutubeVideo;
import com.angarron.vframes.data.videos.YoutubeVideoJsonParser;
import com.angarron.vframes.data.videos.YoutubeVideosModel;
import com.crashlytics.android.Crashlytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeVideosLoader {

    private Listener listener;
    private RecommendedVideosModel recommendedVideosModel;

    public YoutubeVideosLoader(Listener listener) {
        this.listener = listener;
    }

    public void loadVideos(RecommendedVideosModel recommendedVideosModel) {
        this.recommendedVideosModel = recommendedVideosModel;

        //Consolidate list of recommended video IDs so we don't have to make multiple calls.
        List<String> recommendedVideoIds = new ArrayList<>();
        Map<String, List<RecommendedVideo>> recommendedVideos = recommendedVideosModel.getVideos();
        for (String category : recommendedVideos.keySet()) {
            for (RecommendedVideo recommendedVideo : recommendedVideos.get(category)) {
                recommendedVideoIds.add(recommendedVideo.getVideoId());
            }
        }

        //Create a comma-separated list of the IDs.
        StringBuilder commaSeparatedIdsBuilder = new StringBuilder();
        for (int i = 0; i < recommendedVideoIds.size(); i++) {
            commaSeparatedIdsBuilder.append(recommendedVideoIds.get(i));
            if (i != recommendedVideoIds.size() - 1) {
                commaSeparatedIdsBuilder.append(",");
            }
        }

        String commaSeparatedIds = commaSeparatedIdsBuilder.toString();

        //Make the call to get the videos.
        YoutubeDataApi youtubeDataApi = createYoutubeApi();
        makeYoutubeRESTCall(commaSeparatedIds, youtubeDataApi);
    }

    private void makeYoutubeRESTCall(String commaSeparatedIds, YoutubeDataApi youtubeDataApi) {
        Call<JsonObject> call = youtubeDataApi.getVideosWithIds(commaSeparatedIds);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        YoutubeVideosModel youtubeVideosModel = new YoutubeVideosModel();
                        JsonArray videosJson = response.body().get("items").getAsJsonArray();

                        for (JsonElement videoJsonElement : videosJson) {
                            JsonObject videoJsonObject = videoJsonElement.getAsJsonObject();

                            String videoId = videoJsonObject.get("id").getAsString();
                            String category = recommendedVideosModel.getCategoryForVideo(videoId);
                            String description = recommendedVideosModel.getDescriptionForVideo(videoId);

                            YoutubeVideo video = YoutubeVideoJsonParser.parseYoutubeVideoJson(videoJsonObject, description, videoId);
                            youtubeVideosModel.addVideo(category, video);
                        }

                        listener.onVideosLoaded(youtubeVideosModel);
                    } catch (Exception e) {
                        listener.onFailure();
                        if (!BuildConfig.DEBUG) {
                            Crashlytics.logException(e);
                        }
                    }
                } else {
                    try {
                        if (!BuildConfig.DEBUG) {
                            Crashlytics.logException(new Throwable("failure getting video: " + response.errorBody().string()));
                        }
                    } catch (IOException e) {
                        if (!BuildConfig.DEBUG) {
                            Crashlytics.logException(e);
                        }
                    }

                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (!BuildConfig.DEBUG) {
                    Crashlytics.logException(t);
                }

                listener.onFailure();
            }
        });
    }

    private YoutubeDataApi createYoutubeApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(YoutubeDataApi.class);
    }

    public interface Listener {
        void onVideosLoaded(YoutubeVideosModel youtubeVideosModel);
        void onFailure();
    }
}
