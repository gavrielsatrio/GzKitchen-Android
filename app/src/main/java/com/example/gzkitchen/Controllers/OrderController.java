package com.example.gzkitchen.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderController {
    Context context;
    SharedPreferences sharedPref;

    public OrderController(Context contextParam) {
        this.context = contextParam;
        this.sharedPref = contextParam.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
    }

    public JSONArray getOrders() {
        JSONArray jsonArrayReturn = new JSONArray();
        try {
            JSONArray jsonArrayOrder = new JSONArray(sharedPref.getString("Orders", "[]"));
            for(int i = 0; i < jsonArrayOrder.length(); i++) {
                JSONObject objectOrder = jsonArrayOrder.getJSONObject(i);
                if(objectOrder.getInt("StatusID") != 4) {
                    jsonArrayReturn.put(objectOrder);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArrayReturn;
    }

    public JSONArray getOrderWhere(String column, String value, String whereType) {
        JSONArray jsonArrayReturn = new JSONArray();
        try {
            JSONArray jsonArrayOrder = new JSONArray(sharedPref.getString("Orders", "[]"));
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

    public void updateOrderStatus(String orderID, int statusID) {
        JSONArray jsonArrayUpdate = new JSONArray();
        try {
            JSONArray jsonArrayOrder = new JSONArray(sharedPref.getString("Orders", "[]"));
            for(int i = 0; i < jsonArrayOrder.length(); i++) {
                JSONObject objectOrder = jsonArrayOrder.getJSONObject(i);

                if(objectOrder.getString("ID").equals(orderID)) {
                    objectOrder.put("StatusID", statusID);
                }

                jsonArrayUpdate.put(objectOrder);
            }

            sharedPref.edit().putString("Orders", jsonArrayUpdate.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getLastOrderID() {
        int lastOrderID = 0;

        try {
            JSONArray jsonArrayOrders = new JSONArray(sharedPref.getString("Orders", "[]"));
            if(jsonArrayOrders.length() > 0) {
                lastOrderID = jsonArrayOrders.getJSONObject(jsonArrayOrders.length() - 1).getInt("ID");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lastOrderID;
    }

    public void addOrder(JSONObject objectOrder) {
        try {
            JSONArray jsonArrayOrder = new JSONArray(sharedPref.getString("Orders", "[]"));
            jsonArrayOrder.put(objectOrder);

            sharedPref.edit().putString("Orders", jsonArrayOrder.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
