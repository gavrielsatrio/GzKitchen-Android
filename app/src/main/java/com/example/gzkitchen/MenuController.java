package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONArray getMenusWhere(String column, String value) {
        JSONArray jsonArrayReturn = new JSONArray();

        try {
            JSONArray jsonArrayMenu = new JSONArray(sharedPref.getString("Menus", "defaultValue"));
            for(int i = 0; i < jsonArrayMenu.length(); i++) {
                JSONObject object = jsonArrayMenu.getJSONObject(i);
                if(object.getString(column).equals(value)) {
                    jsonArrayReturn.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayReturn;
    }
}