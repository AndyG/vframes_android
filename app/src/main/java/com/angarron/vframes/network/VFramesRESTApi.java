package com.angarron.vframes.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VFramesRESTApi {
    @GET("/dataModel")
    Call<JsonObject> getData(@Query("endpoint") String endpoint);

    @GET("/dataVersion")
    Call<JsonObject> getDataVersion(@Query("endpoint") String endpoint);

    @GET("/guideVideos")
    Call<JsonArray> getAllGuideVideos();

    @GET("/guideVideos")
    Call<JsonArray> getGuideVideosForCharacter(@Query("character") String character);

    @GET("/tournamentVideos")
    Call<JsonArray> getAllTournamentVideos();

    @GET("/tournamentVideos")
    Call<JsonArray> getTournamentVideosForCharacter(@Query("character") String character);
}
