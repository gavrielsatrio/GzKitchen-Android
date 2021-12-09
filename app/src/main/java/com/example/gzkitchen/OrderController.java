package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

public class OrderController {
    Context context;
    SharedPreferences sharedPref;

    public OrderController(Context contextParam) {
        this.context = contextParam;
        this.sharedPref = contextParam.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
    }

    public JSONArray getOrder() {
        JSONArray jsonArrayReturn = new JSONArray();
        try {
            jsonArrayReturn = new JSONArray(sharedPref.getString("Orders", "defaultValue"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        return jsonArrayReturn;
    }
}
