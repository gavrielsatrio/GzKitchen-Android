package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MemberMainActivity extends AppCompatActivity {

    RecyclerView recViewRecommended;
    RecyclerView recViewPopular;
    RecyclerView recViewEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_main);

        recViewRecommended = findViewById(R.id.memberMainRecViewRecommendedItems);
        recViewPopular = findViewById(R.id.memberMainRecViewMostPopularItems);
        recViewEmployee = findViewById(R.id.memberMainRecViewEmployeeOfTheMonth);

        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(new JSONObject().put("Image", R.drawable.sushi2).put("Name", "Sushi").put("Price", 20000));
            jsonArray.put(new JSONObject().put("Image", R.drawable.okonomiyaki).put("Name", "Okonomiyaki").put("Price", 32000));
            jsonArray.put(new JSONObject().put("Image", R.drawable.onigiri).put("Name", "Onigiri").put("Price", 15000));
            jsonArray.put(new JSONObject().put("Image", R.drawable.ramen).put("Name", "Ramen").put("Price", 27000));

            recViewRecommended.setAdapter(new FoodItemSmallAdapter(MemberMainActivity.this, jsonArray));
            recViewRecommended.setLayoutManager(new LinearLayoutManager(MemberMainActivity.this, LinearLayoutManager.HORIZONTAL, false));

            recViewPopular.setAdapter(new FoodItemSmallAdapter(MemberMainActivity.this, jsonArray));
            recViewPopular.setLayoutManager(new LinearLayoutManager(MemberMainActivity.this, LinearLayoutManager.HORIZONTAL, false));

            JSONArray jsonArrayEmployee = new JSONArray();
            jsonArrayEmployee.put(new JSONObject().put("Name", "Gavriel").put("Month", "October 2021"));
            jsonArrayEmployee.put(new JSONObject().put("Name", "Satrio").put("Month", "November 2021"));
            jsonArrayEmployee.put(new JSONObject().put("Name", "Widjaya").put("Month", "December 2021"));

            recViewEmployee.setAdapter(new EmployeeOfTheMonthAdapter(MemberMainActivity.this, jsonArrayEmployee));
            recViewEmployee.setLayoutManager(new LinearLayoutManager(MemberMainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}