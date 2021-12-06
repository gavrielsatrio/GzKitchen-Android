package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

public class MenuController {
    Context context;
    SharedPreferences sharedPref;

    public MenuController(Context contextParam) {
        this.context = contextParam;
        this.sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
    }

    public JSONArray getMenus() {
        JSONArray jsonArrayMenu = new JSONArray();

        try {
            jsonArrayMenu = new JSONArray(sharedPref.getString("Menus", "defaultValue"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayMenu;
    }
}
