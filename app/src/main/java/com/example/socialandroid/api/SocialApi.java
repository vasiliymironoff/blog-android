package com.example.socialandroid.api;

import com.example.socialandroid.api.model.EditProfile;
import com.example.socialandroid.api.model.Message;
import com.example.socialandroid.api.model.NewPost;
import com.example.socialandroid.api.model.NewUser;
import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.api.model.Profile;
import com.example.socialandroid.api.model.ProfileDetail;
import com.example.socialandroid.api.model.Token;
import com.example.socialandroid.api.model.User;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SocialApi {

    @GET("api/v1/posts/")
    Single<List<Post>> getListPost();

    @POST("api/v1/post/")
    Single<Post> postPost(@Header("Authorization") String token, @Body NewPost newPost);

    @GET("api/v1/user/")
    Single<List<Profile>> getListProfile();

    @POST("api/v1/auth/users/")
    Single<User> createUser(@Body NewUser newUser);

    @POST("api/v1/auth_token/token/login")
    Single<Token> getToken(@Body NewUser newUser);

    @GET("api/v1/profile/{id}/")
    Single<ProfileDetail> getProfileDetail(@Path("id") Integer id);

    @GET("api/v1/auth/users/me")
    Single<User> getMe(@Header("Authorization") String token);

    @POST("api/v1/auth_token/token/logout")
    Single<Void> logout(@Header("Authorization") String token);

    @PUT("api/v1/profile/{id}/")
    Single<ProfileDetail> postCustomProfile(@Path("id") Integer id,
                                            @Header("Authorization") String token,
                                            @Body EditProfile editProfile);

    @GET("api/v1/message/")
    Single<List<Message>> getMessage(@Query("user1") int user1,
                                     @Query("user2") int user2);

    @POST("api/v1/message/")
    Single<Message> postMessage(@Header("Authorization") String token, @Body Message message);
}
