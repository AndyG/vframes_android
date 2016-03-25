package com.angarron.vframes.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VFramesRESTApi {
    @GET("/res/vframes/android/v{version}/characters_model.json")
    Call<JsonObject> getData(@Path("version") Integer version);

    //send "all" to get non-character-specific videos.
    @GET("/guideVideos")
    Call<JsonObject> getAllGuideVideos();

    //send "all" to get non-character-specific videos.
    @GET("/guideVideos")
    Call<JsonArray> getGuideVideosForCharacter(@Query("character") String character);
}
