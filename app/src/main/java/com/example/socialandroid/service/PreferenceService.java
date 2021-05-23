package com.example.socialandroid.service;

import android.content.Context;
import android.preference.PreferenceManager;

public class PreferenceService {
    public static final String TOKEN = "token";
    public static final String ID = "ID";

    public static int getId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(ID, -1);
    }

    public static String getToken(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TOKEN, "");
    }

    public static void putId(Context context, int id){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(ID, id)
                .apply();
    }

    public static void putToken(Context context, String token){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(TOKEN, token)
                .apply();
    }
}
