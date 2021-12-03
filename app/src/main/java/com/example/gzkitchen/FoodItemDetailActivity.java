package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    LinearLayout linearLayoutFoodDetails;
    TextView lblFoodDetailDesc;

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
        linearLayoutFoodDetails = findViewById(R.id.foodItemDetailLinearLayoutFoodDetail);
        lblFoodDetailDesc = findViewById(R.id.foodItemDetailLblDetailDesc);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodItemDetailActivity.super.onBackPressed();
            }
        });

        try {
            JSONObject object = new JSONObject(getIntent().getStringExtra("Object"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imgFood.setImageDrawable(getDrawable(object.getInt("Image")));
            } else {
                imgFood.setImageDrawable(getResources().getDrawable(object.getInt("Image")));
            }
            lblName.setText(object.getString("Name"));
            lblDesc.setText(object.getString("Description"));
            lblFoodDetailDesc.setText("1 portion of this " + object.getString("Name") + " contains :");

            int price = object.getInt("Price");
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(0);

            lblPrice.setText(formatter.format(price).replace("$", "Rp"));


            JSONArray jsonArrayIngredients = object.getJSONArray("Ingredients");
            recViewIngredients.setAdapter(new IngredientsAdapter(FoodItemDetailActivity.this, jsonArrayIngredients));
            recViewIngredients.setLayoutManager(new LinearLayoutManager(FoodItemDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));


            JSONArray jsonArrayFoodDetails = object.getJSONArray("Details");
            for(int i = 0; i < jsonArrayFoodDetails.length(); i++) {
                View viewFoodDetail = LayoutInflater.from(FoodItemDetailActivity.this).inflate(R.layout.food_details_layout, null, false);
                ((TextView)viewFoodDetail.findViewById(R.id.foodDetailsLayoutLbl)).setText(jsonArrayFoodDetails.getString(i));

                linearLayoutFoodDetails.addView(viewFoodDetail);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}