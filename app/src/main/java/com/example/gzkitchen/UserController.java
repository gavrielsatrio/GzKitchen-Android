package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserController {
    public JSONObject getUserObjectByEmail(Context context, String email) {
        SharedPreferences sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
        JSONObject objectUserReturn = null;

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

    public void logoutUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
        sharedPref.edit().remove("LoggedInUserEmail").apply();
    }

    public JSONObject getLoggedInUserObject(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
        String email = sharedPref.getString("LoggedInUserEmail", "defaultValue");
        JSONObject objectUserReturn = null;

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

    public void updateUserProfile(Context context, String nameUpdate, String emailUpdate, String passwordUpdate) {
        SharedPreferences sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
        String loggedInEmail = sharedPref.getString("LoggedInUserEmail", "defaultValue");

        try {
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));

            for(int i = 0; i < jsonArrayUsers.length(); i++) {
                JSONObject objectUser = jsonArrayUsers.getJSONObject(i);
                if(objectUser.getString("Email").equals(loggedInEmail)) {
                    jsonArrayUsers.getJSONObject(i).put("Name", nameUpdate);
                    jsonArrayUsers.getJSONObject(i).put("Email", emailUpdate);
                    jsonArrayUsers.getJSONObject(i).put("Password", passwordUpdate);

                    break;
                }
            }

            sharedPref.edit().putString("Users", jsonArrayUsers.toString()).apply();
            sharedPref.edit().putString("LoggedInUserEmail", emailUpdate).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean checkEmailAvailable(Context context) {
        boolean isAvailable = false;

        SharedPreferences sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
        try {
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isAvailable;
    }
}
