package com.angarron.vframes.network;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface VFramesRESTApi {
    @GET("/res/vframes/android/v{version}/characters_model.json")
    Call<JsonObject> getData(@Path("version") Integer version);
}
