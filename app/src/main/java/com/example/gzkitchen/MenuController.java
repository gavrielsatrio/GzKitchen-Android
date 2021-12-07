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
                if(object.getString(column).toLowerCase().contains(value.toLowerCase())) {
                    jsonArrayReturn.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayReturn;
    }

    public JSONArray getRecentlyAddedMenus() {
        JSONArray jsonArrayReturn = new JSONArray();

        try {
            JSONArray jsonArrayMenu = new JSONArray(sharedPref.getString("Menus", "defaultValue"));
            int lastIndex = jsonArrayMenu.length() - 1;

            if(jsonArrayMenu.length() >= 3) {
                for(int i = lastIndex; i >= lastIndex - 2; i--) {
                    JSONObject object = jsonArrayMenu.getJSONObject(i);
                    jsonArrayReturn.put(object);
                }
            } else {
                jsonArrayReturn = jsonArrayMenu;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayReturn;
    }

    public void addMenu(JSONObject objectMenu) {
        try {
            JSONArray jsonArrayMenu = new JSONArray(sharedPref.getString("Menus", "defaultValue"));
            jsonArrayMenu.put(objectMenu);

            sharedPref.edit().putString("Menus", jsonArrayMenu.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void editMenu(String menuID, JSONObject objectMenu) {
        try {
            JSONArray jsonArrayMenuUpdate = new JSONArray();
            JSONArray jsonArrayMenu = new JSONArray(sharedPref.getString("Menus", "defaultValue"));

            for(int i = 0; i < jsonArrayMenu.length(); i++) {
                JSONObject objectMenuUpdate = jsonArrayMenu.getJSONObject(i);

                if(objectMenuUpdate.getString("ID").equals(menuID)) {
                    objectMenuUpdate = objectMenu;
                }

                jsonArrayMenuUpdate.put(objectMenuUpdate);
            }

            sharedPref.edit().putString("Menus", jsonArrayMenuUpdate.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteMenu(String menuID) {
        try {
            JSONArray jsonArrayMenuUpdate = new JSONArray();
            JSONArray jsonArrayMenu = new JSONArray(sharedPref.getString("Menus", "defaultValue"));

            for(int i = 0; i < jsonArrayMenu.length(); i++) {
                JSONObject objectMenuUpdate = jsonArrayMenu.getJSONObject(i);

                if(!objectMenuUpdate.getString("ID").equals(menuID)) {
                    jsonArrayMenuUpdate.put(objectMenuUpdate);
                }
            }

            sharedPref.edit().putString("Menus", jsonArrayMenuUpdate.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
