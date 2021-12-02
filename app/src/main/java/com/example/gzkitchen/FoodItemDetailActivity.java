package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodItemDetailActivity extends AppCompatActivity {

    ImageView imgFood;
    TextView lblName;
    TextView lblDesc;
    TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_detail);

        imgFood = findViewById(R.id.foodItemDetailImg);
        lblName = findViewById(R.id.foodItemDetailLblName);
        lblDesc = findViewById(R.id.foodItemDetailLblDescriptionValue);
        btnBack = findViewById(R.id.foodItemDetailBtnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodItemDetailActivity.super.onBackPressed();
            }
        });

        try {
            JSONObject object = new JSONObject(getIntent().getStringExtra("Object"));
            imgFood.setImageDrawable(getDrawable(object.getInt("Image")));
            lblName.setText(object.getString("Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}