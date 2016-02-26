package com.angarron.vframes.network;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface TwitchRESTApi {
    @GET("streams")
    Call<JsonObject> searchForGame(@Query("game") String game);
}
