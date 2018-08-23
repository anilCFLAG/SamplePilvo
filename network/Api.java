package com.example.plivo.sample1.network;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Define the end points
 */
public interface Api {

    @POST("/Account/{auth_id}/Call")
    Call<CallResponseObj> makeCall(@Body CallPostObj call, @Path("auth_id") String auth_id);

    @DELETE("/Account/{auth_id}/Call/{call_uuid}/")
    Call<JsonElement> endCall(@Path("auth_id") String authId, @Path("call_uuid") String callUUid);
}
