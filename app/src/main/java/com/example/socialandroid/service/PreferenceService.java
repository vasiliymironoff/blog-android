package com.example.socialandroid.service;

import android.content.Context;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

public class PreferenceService {
    public static final String TOKEN = "token";
    public static final String ID = "ID";
    public static final String USERNAME = "USERNAME";
    public static final String IMAGE = "IMAGE";

    public static int getId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(ID, -1);
    }

    public static String getToken(Context context) {
        return "Token " + PreferenceManager.getDefaultSharedPreferences(context).getString(TOKEN, "");
    }

    public static String getUsername(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(USERNAME, "");
    }

    public static String getImage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(IMAGE, "");
    }

    public static void putImage(Context context, String image) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(IMAGE, image)
                .apply();
    }

    public static void putUserName(Context context, String username) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(USERNAME, username)
                .apply();
    }

    public static void putId(Context context, int id) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(ID, id)
                .apply();
    }

    public static void putToken(Context context, String token) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(TOKEN, token)
                .apply();
    }
}
