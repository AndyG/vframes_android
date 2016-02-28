package com.angarron.vframes.network;

import com.angarron.vframes.BuildConfig;
import com.angarron.vframes.data.videos.YoutubeVideo;
import com.angarron.vframes.data.videos.YoutubeVideoJsonParser;
import com.crashlytics.android.Crashlytics;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoadVideoTask {

    private ILoadVideoTaskListener listener;


    public LoadVideoTask(ILoadVideoTaskListener listener) {
        this.listener = listener;
    }

    public void getVideoWithId(final String videoId, YoutubeDataApi youtubeDataApi) {
        Call<JsonObject> call = youtubeDataApi.getVideoWithId(videoId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    try {
                        //This is a request that should only return one response so grab the "first" item
                        JsonObject youtubeJson = response.body().get("items").getAsJsonArray().get(0).getAsJsonObject();
                        YoutubeVideo video = YoutubeVideoJsonParser.parseYoutubeVideoJson(videoId, youtubeJson);
                        listener.onVideoLoaded(video);
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
            public void onFailure(Throwable t) {
                if (!BuildConfig.DEBUG) {
                    Crashlytics.logException(t);
                }

                listener.onFailure();
            }
        });
    }

    public interface ILoadVideoTaskListener {
        void onVideoLoaded(YoutubeVideo video);
        void onFailure();
    }
}