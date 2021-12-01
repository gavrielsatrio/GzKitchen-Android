package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Member;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    TextView lblHeader;
    TextView lblDesc;
    ImageView imgRedMoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        lblHeader = findViewById(R.id.loadingLblHeader);
        lblDesc = findViewById(R.id.loadingLblDesc);
        imgRedMoon = findViewById(R.id.loadingImgRedMoon);

        lblHeader.animate().translationX(0).alpha(1).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator());
        lblDesc.animate().translationX(0).alpha(1).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator());
        imgRedMoon.animate().translationY(0).setDuration(600).setStartDelay(500).setInterpolator(new AccelerateDecelerateInterpolator());

        SharedPreferences sharedPref = getSharedPreferences("Info", Context.MODE_PRIVATE);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(sharedPref.getString("IsFirstUse", "true").equals("true")) {
                    // First Install or First Use
                    sharedPref.edit()
                            .putString("IsFirstUse", "false")
                            .apply();
                } else {
                    // Already used at least once
                }

                Intent intent = new Intent(LoadingActivity.this, SplashScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1800);
    }
}