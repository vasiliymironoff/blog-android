package com.example.socialandroid.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    public static final String TOKEN = "token";
    public static final String isReg = "isRegister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean(isReg, false)) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}