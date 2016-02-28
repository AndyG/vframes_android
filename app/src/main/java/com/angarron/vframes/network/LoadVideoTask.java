package com.angarron.vframes.network;

import android.util.Log;

import com.angarron.vframes.data.videos.YoutubeVideo;
import com.angarron.vframes.data.videos.YoutubeVideoJsonParser;
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
                    //This is a request that should only return one response so grab the "first" item
                    JsonObject youtubeJson = response.body().get("items").getAsJsonArray().get(0).getAsJsonObject();
                    YoutubeVideo video = YoutubeVideoJsonParser.parseYoutubeVideoJson(videoId, youtubeJson);
                    listener.onVideoLoaded(video);
                } else {
                    try {
                        Log.d("findme", "onResponse failure: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.d("findme", "onResponse double failure");
                    }

                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("findme", "onFailure: " + t.getMessage());
                listener.onFailure();
            }
        });
    }

    public interface ILoadVideoTaskListener {
        void onVideoLoaded(YoutubeVideo video);
        void onFailure();
    }
}