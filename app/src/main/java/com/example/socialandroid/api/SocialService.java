package com.example.socialandroid.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SocialService {
    public SocialApi socialApi;

    public SocialService(){
        Retrofit retrofit = createRetrofit();
        socialApi = retrofit.create(SocialApi.class);
        Log.d("tag", "createApi");
    }

    public SocialApi getApi(){
        Log.d("tag", "getApi");
        return socialApi;
    }

    private Retrofit createRetrofit(){
        Log.d("tag", "createRetrofit");
        return new Retrofit.Builder()
                .baseUrl("http://192.168.31.150:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(createHttpClient())
                .build();
    }

    private OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }
}
