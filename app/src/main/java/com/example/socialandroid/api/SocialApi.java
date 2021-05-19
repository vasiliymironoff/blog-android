package com.example.socialandroid.api;

import com.example.socialandroid.api.model.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SocialApi {

    @GET("post/")
    Single<List<Post>> listPost();
}
