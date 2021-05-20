package com.example.socialandroid.api;

import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.api.model.Profile;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SocialApi {

    @GET("api/v1/post/")
    Single<List<Post>> getListPost();

    @GET("api/v1/user/")
    Single<List<Profile>> getListProfile();

}
