package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserController {
    public JSONObject getUserObjectByEmail(Context context, String email) {
        SharedPreferences sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
        JSONObject objectUserReturn = new JSONObject();

        try {
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));

            for(int i = 0; i < jsonArrayUsers.length(); i++) {
                JSONObject objectUser = jsonArrayUsers.getJSONObject(i);
                if(email.equals(objectUser.getString("Email"))) {
                    objectUserReturn = objectUser;
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objectUserReturn;
    }
}
