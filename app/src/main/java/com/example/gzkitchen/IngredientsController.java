package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

public class IngredientsController {
    Context context;
    SharedPreferences sharedPref;

    public IngredientsController(Context contextParam) {
        this.context = contextParam;
        this.sharedPref = contextParam.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
    }

    public JSONArray getIngredients() {
        JSONArray jsonArrayIngredients = new JSONArray();

        try {
            jsonArrayIngredients = new JSONArray(sharedPref.getString("Ingredients", "defaultValue"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayIngredients;
    }
}
