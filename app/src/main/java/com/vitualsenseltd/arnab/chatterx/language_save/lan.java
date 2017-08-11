package com.vitualsenseltd.arnab.chatterx.language_save;

import com.vitualsenseltd.arnab.chatterx.Translation.authPOJO;
import com.vitualsenseltd.arnab.chatterx.UserDetails;

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

public interface lan {
    @GET("language.json")
    Call<lang> readlang(@Query("print")String lang);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://chatterx-7db2d.firebaseio.com/users/"+ UserDetails.username+"/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
