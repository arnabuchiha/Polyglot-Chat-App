package com.vitualsenseltd.arnab.chatterx.Translation;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by arnab on 8/2/2017.
 */

public interface translate {
    @GET("v2")
    Call<authPOJO> performTranslate(@Query("key")String key,
                                    @Query("q") String q,
                                    @Query("source") String source,
                                    @Query("target") String target);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/language/translate/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
