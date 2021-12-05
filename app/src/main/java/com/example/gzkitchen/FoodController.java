package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

public class FoodController {
    Context context;
    SharedPreferences sharedPref;

    public FoodController(Context contextParam) {
        this.context = contextParam;
        this.sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
    }

    public JSONArray getFoods() {
        JSONArray jsonArrayFood = new JSONArray();

        try {
            jsonArrayFood = new JSONArray(sharedPref.getString("Foods", "defaultValue"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayFood;
    }
}
