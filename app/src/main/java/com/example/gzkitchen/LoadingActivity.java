package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        LoadAnimation();

        SharedPreferences sharedPref = getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(sharedPref.getString("IsFirstUse", "true").equals("true")) {
                    // First Install or First Use
                    try {
                        JSONArray jsonArrayUsers = new JSONArray();
                        jsonArrayUsers
                        .put(new JSONObject()
                                .put("Name","Admin")
                                .put("Email", "admin@gmail.com")
                                .put("Password", "admin123")
                                .put("Role", "Admin")
                        ).put(new JSONObject()
                                .put("Name","Cashier")
                                .put("Email", "cashier@gmail.com")
                                .put("Password", "admin123")
                                .put("Role", "Cashier")
                        ).put(new JSONObject()
                                .put("Name","Chef")
                                .put("Email", "chef@gmail.com")
                                .put("Password", "chef123")
                                .put("Role", "Chef")
                        );

                        sharedPref.edit()
                                .putString("Users", jsonArrayUsers.toString())
                                .apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(LoadingActivity.this, SplashScreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Already used at least once

                    String loggedInUserEmail = sharedPref.getString("LoggedInUserEmail", "defaultValue");
                    if(!loggedInUserEmail.equals("defaultValue")) {
                        // Already logged in before

                        JSONObject objectUser = new UserController().getUserObjectByEmail(LoadingActivity.this, loggedInUserEmail);
                        try {
                            String userRole = objectUser.getString("Role");
                            if(userRole.equals("Admin")) {
                                Intent intent = new Intent(LoadingActivity.this, AdminMainActivity.class);
                                intent.putExtra("Email", loggedInUserEmail);
                                startActivity(intent);
                            } else if (userRole.equals("Cashier")) {
                                Intent intent = new Intent(LoadingActivity.this, MemberMainActivity.class);
                                intent.putExtra("Email", loggedInUserEmail);
                                startActivity(intent);
                            } else if(userRole.equals("Chef")) {
                                Intent intent = new Intent(LoadingActivity.this, MemberMainActivity.class);
                                intent.putExtra("Email", loggedInUserEmail);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(LoadingActivity.this, MemberMainActivity.class);
                                intent.putExtra("Email", loggedInUserEmail);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Never logged in before
                        Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    finish();
                }
            }
        }, 1800);
    }

    private void LoadAnimation() {
        lblHeader.animate().translationX(0).alpha(1).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator());
        lblDesc.animate().translationX(0).alpha(1).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator());
        imgRedMoon.animate().translationY(0).setDuration(600).setStartDelay(500).setInterpolator(new AccelerateDecelerateInterpolator());
    }
}