package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderHeaderController {
    Context context;
    SharedPreferences sharedPref;

    public OrderHeaderController(Context contextParam) {
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
        return jsonArrayReturn;
    }

    public JSONArray getOrderWhere(String column, String value, String whereType) {
        JSONArray jsonArrayReturn = new JSONArray();
        try {
            JSONArray jsonArrayOrder = new JSONArray(sharedPref.getString("Orders", "defaultValue"));
            for(int i = 0; i < jsonArrayOrder.length(); i++) {
                JSONObject objectOrder = jsonArrayOrder.getJSONObject(i);
                if(whereType.equals("contains")) {
                    if(objectOrder.getString(column).contains(value)) {
                        jsonArrayReturn.put(objectOrder);
                    }
                } else {
                    if(objectOrder.getString(column).equals(value)) {
                        jsonArrayReturn.put(objectOrder);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArrayReturn;
    }

    public void updateOrderStatus(String orderID, String status) {
        JSONArray jsonArrayUpdate = new JSONArray();
        try {
            JSONArray jsonArrayOrder = new JSONArray(sharedPref.getString("Orders", "defaultValue"));
            for(int i = 0; i < jsonArrayOrder.length(); i++) {
                JSONObject objectOrder = jsonArrayOrder.getJSONObject(i);

                if(objectOrder.getString("ID").equals(orderID)) {
                    objectOrder.put("Status", status);
                }

                jsonArrayUpdate.put(objectOrder);
            }

            sharedPref.edit().putString("Orders", jsonArrayUpdate.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(JSONObject objectOrder) {
        try {
            JSONArray jsonArrayOrder = new JSONArray(sharedPref.getString("Orders", "defaultValue"));
            jsonArrayOrder.put(objectOrder);

            sharedPref.edit().putString("Orders", jsonArrayOrder.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
