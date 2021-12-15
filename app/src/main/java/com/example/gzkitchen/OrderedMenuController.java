package com.example.gzkitchen;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderedMenuController {
    public JSONArray editMenuInOrder(JSONArray jsonArrayOrderedMenu, JSONObject objectAddMenu) {
        boolean isMenuAlreadyInOrderList = false;

        int indexUpdate = 0;
        for(int i = 0; i < jsonArrayOrderedMenu.length(); i++) {
            try {
                JSONObject objectMenu = jsonArrayOrderedMenu.getJSONObject(i);
                if(objectMenu.getInt("MenuID") == objectAddMenu.getInt("MenuID")) {
                    isMenuAlreadyInOrderList = true;
                    indexUpdate = i;
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(isMenuAlreadyInOrderList) {
            try {
                if(objectAddMenu.getInt("Qty") == 0) {
                    jsonArrayOrderedMenu.remove(indexUpdate);
                } else {
                    jsonArrayOrderedMenu.getJSONObject(indexUpdate).put("Qty", objectAddMenu.getInt("Qty"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if(objectAddMenu.getInt("Qty") > 0) {
                    jsonArrayOrderedMenu.put(objectAddMenu);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArrayOrderedMenu;
    }
}
