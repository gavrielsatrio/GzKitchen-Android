package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserController {
    Context context;
    SharedPreferences sharedPref;

    public UserController(Context contextParam) {
        this.context = contextParam;
        this.sharedPref = context.getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
    }

    public JSONObject getUserObjectByEmail(String email) {
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

    public void logoutUser() {
        sharedPref.edit().remove("LoggedInUserEmail").apply();
    }

    public JSONObject getLoggedInUserObject() {
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

    public void updateUserProfile(String nameUpdate, String emailUpdate, String passwordUpdate) {
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

    public JSONArray getUserWhere(String column, String value) {
        JSONArray jsonArrayReturn = new JSONArray();

        try {
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));
            for(int i = 0; i < jsonArrayUsers.length(); i++) {
                JSONObject object = jsonArrayUsers.getJSONObject(i);
                if(object.getString(column).equals(value)) {
                    jsonArrayReturn.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayReturn;
    }

    public boolean checkEmailAvailable(String email) {
        boolean isAvailable = false;

        try {
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));
            for(int i = 0; i < jsonArrayUsers.length(); i++) {
                JSONObject object = jsonArrayUsers.getJSONObject(i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isAvailable;
    }
}
