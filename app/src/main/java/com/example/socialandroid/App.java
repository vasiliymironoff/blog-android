package com.example.socialandroid;

import android.app.Application;

import com.example.socialandroid.api.SocialService;

public class App extends Application {

    static SocialService service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = new SocialService();
    }

    public static SocialService getService() {
        return service;
    }

}
