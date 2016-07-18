package com.angarron.vframes.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeDataApi {
    @GET("/youtube/v3/videos?key=AIzaSyBP5y-qxNYYBUjOPHXQxaVhzG4YqY6tVPM&part=snippet")
    Call<JsonObject> getVideosWithIds(@Query("id") String videoId);
}
