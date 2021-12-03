package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class FoodItemDetailActivity extends AppCompatActivity {

    ImageView imgFood;
    TextView lblName;
    TextView lblDesc;
    TextView btnBack;
    TextView lblPrice;
    Button btnOrder;
    RecyclerView recViewIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_detail);

        imgFood = findViewById(R.id.foodItemDetailImg);
        lblName = findViewById(R.id.foodItemDetailLblName);
        lblDesc = findViewById(R.id.foodItemDetailLblDescriptionValue);
        btnBack = findViewById(R.id.foodItemDetailBtnBack);
        lblPrice = findViewById(R.id.foodItemDetailLblPrice);
        btnOrder = findViewById(R.id.foodItemDetailBtnOrder);
        recViewIngredients = findViewById(R.id.foodItemDetailRecViewIngredients);

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
            lblDesc.setText(object.getString("Description"));

            int price = object.getInt("Price");
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(0);

            lblPrice.setText(formatter.format(price).replace("$", "Rp"));


            JSONArray jsonArray = new JSONArray("[1, 2, 3, 4, 5]");

            recViewIngredients.setAdapter(new IngredientsAdapter(FoodItemDetailActivity.this, jsonArray));
            recViewIngredients.setLayoutManager(new LinearLayoutManager(FoodItemDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}