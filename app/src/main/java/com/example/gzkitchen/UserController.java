package com.example.gzkitchen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

    public void updateUserImage(Bitmap bitmapImage) {
        try {
            String userID = getLoggedInUserObject().getString("ID");

            JSONArray jsonArrayUpdate = new JSONArray();
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));

            for(int i = 0; i < jsonArrayUsers.length(); i++) {
                JSONObject objectUser = jsonArrayUsers.getJSONObject(i);
                if(objectUser.getString("ID").equals(userID)) {
                    objectUser.put("Image", new BitmapHelper().convertToBase64String(bitmapImage));
                }

                jsonArrayUpdate.put(objectUser);
            }

            sharedPref.edit().putString("Users", jsonArrayUpdate.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getLastUserID() {
        int lastUserID = 0;
        try {
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));
            lastUserID = jsonArrayUsers.getJSONObject(jsonArrayUsers.length() - 1).getInt("ID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lastUserID;
    }

    public void updateRole(String userID, String role) {
        try {
            JSONArray jsonArrayUpdate = new JSONArray();
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));

            for(int i = 0; i < jsonArrayUsers.length(); i++) {
                JSONObject objectUser = jsonArrayUsers.getJSONObject(i);
                if(objectUser.getString("ID").equals(userID)) {
                    objectUser.put("Role", role);
                }

                jsonArrayUpdate.put(objectUser);
            }

            sharedPref.edit().putString("Users", jsonArrayUpdate.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getAllUsers() {
        JSONArray jsonArrayReturn = new JSONArray();

        try {
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));
            for(int i = 0; i < jsonArrayUsers.length(); i++) {
                JSONObject object = jsonArrayUsers.getJSONObject(i);
                if(!object.getString("Role").equals("Admin")) {
                    jsonArrayReturn.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayReturn;
    }

    public JSONArray getSearchUser(String search) {
        JSONArray jsonArrayReturn = new JSONArray();

        try {
            JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));
            for(int i = 0; i < jsonArrayUsers.length(); i++) {
                JSONObject object = jsonArrayUsers.getJSONObject(i);
                if(!object.getString("Role").equals("Admin") && (object.getString("Name").toLowerCase().contains(search.toLowerCase()) || object.getString("Email").toLowerCase().contains(search.toLowerCase()) || object.getString("Role").toLowerCase().contains(search.toLowerCase()))) {
                    jsonArrayReturn.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArrayReturn;
    }
}
