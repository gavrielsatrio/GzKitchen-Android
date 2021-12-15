package com.example.gzkitchen;

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
                jsonArrayOrderedMenu.getJSONObject(indexUpdate).put("Qty", objectAddMenu.getInt("Qty"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            jsonArrayOrderedMenu.put(objectAddMenu);
        }

        return jsonArrayOrderedMenu;
    }
}
