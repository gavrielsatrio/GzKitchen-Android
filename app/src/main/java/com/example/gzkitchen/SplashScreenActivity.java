package com.example.gzkitchen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreenActivity extends AppCompatActivity {

    RecyclerView recView;
    TextView btnNextFinish;
    LinearLayout linearLayoutDot;
    int selectedPosition = 0;
    JSONArray splashScreenJSONArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        recView = findViewById(R.id.splashScreenRecView);
        btnNextFinish = findViewById(R.id.splashScreenBtnNextFinish);
        linearLayoutDot = findViewById(R.id.splashScreenLinearLayoutDot);

        LinearLayoutManager layoutManager = new LinearLayoutManager(SplashScreenActivity.this, RecyclerView.HORIZONTAL, false);
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(new JSONObject().put("Image", R.drawable.ic_japan).put("Title", "What is GzKitchen ?").put("Desc", "GzKitchen is a japanese style restaurant. Here, you can find all kinds of japanese food"));
            jsonArray.put(new JSONObject().put("Image", R.drawable.ic_chef).put("Title", "Fresh & Delicious Food").put("Desc", "We provides you the best quality and freshly cooked food by the best chef from Japan"));
            jsonArray.put(new JSONObject().put("Image", R.drawable.ic_confirmed).put("Title", "Fast Service").put("Desc", "Once we placed your order, it will be sent directly to the chef and be prepared for you right away"));

            splashScreenJSONArray = jsonArray;

            recView.setAdapter(new SplashScreenRecViewAdapter(SplashScreenActivity.this, jsonArray));
            recView.setLayoutManager(layoutManager);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recView);

            for(int i = 0; i < splashScreenJSONArray.length(); i++) {
                View viewDot = LayoutInflater.from(SplashScreenActivity.this).inflate(R.layout.splash_screen_dot_layout, null, false);

                if(i == selectedPosition) {
                    ((ImageView)viewDot.findViewById(R.id.splashScreenDotLayoutImg)).setImageDrawable(getDrawable(R.drawable.dot_selected));
                }

                linearLayoutDot.addView(viewDot);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                selectedPosition = layoutManager.findLastVisibleItemPosition();
                LoadButtonNextFinish();
            }
        });



        btnNextFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPosition < 2) {
                    selectedPosition++;
                    recView.smoothScrollToPosition(selectedPosition);
                    LoadButtonNextFinish();
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void LoadButtonNextFinish() {
        if(selectedPosition == 2) {
            btnNextFinish.setText("Finish");
        } else {
            btnNextFinish.setText("Next >");
        }

        LoadSliderDot();
    }

    private void LoadSliderDot() {
        for(int i = 0; i < splashScreenJSONArray.length(); i++) {
            View viewDot = linearLayoutDot.getChildAt(i);
            ((ImageView)viewDot.findViewById(R.id.splashScreenDotLayoutImg)).setImageDrawable(getDrawable(R.drawable.dot_unselected));
        }

        View viewDotSelected = linearLayoutDot.getChildAt(selectedPosition);
        ((ImageView)viewDotSelected.findViewById(R.id.splashScreenDotLayoutImg)).setImageDrawable(getDrawable(R.drawable.dot_selected));
    }
}