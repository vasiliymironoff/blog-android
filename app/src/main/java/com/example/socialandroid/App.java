package com.example.socialandroid;

import android.app.Application;

import com.example.socialandroid.api.SocialService;
import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.api.model.Profile;

import java.util.List;

public class App extends Application {

    static SocialService service;
    public static List<Post> posts;
    public static List<Profile> profiles;

    @Override
    public void onCreate() {
        super.onCreate();
        service = new SocialService();
    }

    public static SocialService getService() {
        return service;
    }

}
