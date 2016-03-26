package com.angarron.vframes.network;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.data.videos.IVideo;
import com.angarron.vframes.data.videos.YoutubeVideo;
import com.angarron.vframes.data.videos.YoutubeVideoJsonParser;
import com.crashlytics.android.Crashlytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeVideosLoader {

    private Listener listener;
    private Map<String, String> idSubtextMap = new HashMap<>();

    public YoutubeVideosLoader(Listener listener) {
        this.listener = listener;
    }

    public void loadVideos(List<? extends IVideo> videos) {

        //Create a comma-separated list of the IDs.
        StringBuilder commaSeparatedIdsBuilder = new StringBuilder();
        for (int i = 0; i < videos.size(); i++) {
            IVideo video = videos.get(i);
            idSubtextMap.put(video.getVideoID(), video.getSubtext());
            commaSeparatedIdsBuilder.append(videos.get(i).getVideoID());
            if (i != videos.size() - 1) {
                commaSeparatedIdsBuilder.append(",");
            }
        }

        String commaSeparatedIds = commaSeparatedIdsBuilder.toString();

        //Make the call to get the videos.
        YoutubeDataApi youtubeDataApi = createYoutubeApi();
        makeYoutubeRESTCall(commaSeparatedIds, youtubeDataApi);
    }

    private void makeYoutubeRESTCall(String commaSeparatedIds, final YoutubeDataApi youtubeDataApi) {
        Call<JsonObject> call = youtubeDataApi.getVideosWithIds(commaSeparatedIds);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        List<YoutubeVideo> youtubeVideos = new ArrayList<>();
                        JsonArray videosJson = response.body().get("items").getAsJsonArray();

                        for (JsonElement videoJsonElement : videosJson) {
                            JsonObject videoJsonObject = videoJsonElement.getAsJsonObject();

                            String videoId = videoJsonObject.get("id").getAsString();
                            String subtext = idSubtextMap.get(videoId);

                            YoutubeVideo video = YoutubeVideoJsonParser.parseYoutubeVideoJson(videoJsonObject, subtext, videoId);
                            youtubeVideos.add(video);
                        }

                        listener.onVideosLoaded(youtubeVideos);
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
        void onVideosLoaded(List<YoutubeVideo> youtubeVideos);
        void onFailure();
    }
}
