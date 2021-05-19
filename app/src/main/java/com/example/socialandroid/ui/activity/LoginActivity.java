package com.example.socialandroid.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.socialandroid.R;

import java.util.concurrent.Callable;

public class LoginActivity extends AppCompatActivity{

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navController = navHostFragment.getNavController();
    }

}