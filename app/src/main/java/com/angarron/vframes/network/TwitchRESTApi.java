package com.angarron.vframes.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TwitchRESTApi {
    @GET("streams")
    Call<JsonObject> searchForGame(@Query("game") String game);
}
