package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    TextView lblHeader;
    TextView lblDesc;
    ImageView imgRedMoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        lblHeader = findViewById(R.id.loadingLblHeader);
        lblDesc = findViewById(R.id.loadingLblDesc);
        imgRedMoon = findViewById(R.id.loadingImgRedMoon);

        LoadAnimation();

        SharedPreferences sharedPref = getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(sharedPref.getString("IsFirstUse", "true").equals("true")) {
                    // First Install or First Use
                    try {
                        JSONArray jsonArrayUsers = new JSONArray();
                        jsonArrayUsers
                        .put(new JSONObject()
                                .put("ID", 1)
                                .put("Image", "")
                                .put("Name","Admin")
                                .put("Email", "admin@gmail.com")
                                .put("Password", "admin123")
                                .put("Role", "Admin")
                        ).put(new JSONObject()
                                .put("ID", 2)
                                .put("Image", "")
                                .put("Name","Cashier")
                                .put("Email", "cashier@gmail.com")
                                .put("Password", "cashier123")
                                .put("Role", "Cashier")
                        ).put(new JSONObject()
                                .put("ID", 3)
                                .put("Image", "")
                                .put("Name","Chef")
                                .put("Email", "chef@gmail.com")
                                .put("Password", "chef123")
                                .put("Role", "Chef")
                        );

                        sharedPref.edit()
                                .putString("Users", jsonArrayUsers.toString())
                                .apply();

                        Bitmap[] menuBitmap = {
                                BitmapFactory.decodeResource(getResources(), R.drawable.sushi2),
                                BitmapFactory.decodeResource(getResources(), R.drawable.okonomiyaki),
                                BitmapFactory.decodeResource(getResources(), R.drawable.onigiri),
                                BitmapFactory.decodeResource(getResources(), R.drawable.ramen)
                        };

                        BitmapHelper bitmapHelper = new BitmapHelper();

                        JSONArray jsonArrayMenu = new JSONArray();
                        jsonArrayMenu.put(
                                new JSONObject().put("ID", 1).put("Image", bitmapHelper.convertToBase64String(menuBitmap[0])).put("Name", "Sushi").put("Price", 20000).put("Description", "This sushi is specially made for you by the chef. It uses the best quality of salmon dan tuna. The nori is still crispy and delicious")
                                        .put("Ingredients",
                                                new JSONArray()
                                                        .put(new JSONObject().put("ID", 1).put("Name", "Rice"))
                                                        .put(new JSONObject().put("ID", 2).put("Name", "Shrimp"))
                                                        .put(new JSONObject().put("ID", 3).put("Name", "Tuna"))
                                                        .put(new JSONObject().put("ID", 4).put("Name", "Salmon"))
                                                        .put(new JSONObject().put("ID", 5).put("Name", "Egg"))
                                                        .put(new JSONObject().put("ID", 6).put("Name", "Squid"))
                                                        .put(new JSONObject().put("ID", 7).put("Name", "Nori"))
                                        ).put("Details",
                                        new JSONArray()
                                                .put("1 pc of Ika nigiri")
                                                .put("1 pc of Sake nigiri")
                                                .put("1 pc of Tamagoyaki")
                                                .put("1 pc of Unagi")
                                                .put("1 pc of Ebi nigiri")
                                                .put("1 pc of Tobiko nigiri")
                                                .put("1 pc of Uni")
                                                .put("1 pc of Amaebi")
                                                .put("2 pcs of Tekkamaki")
                                                .put("2 pcs of Kappa maki")
                                )
                        );
                        jsonArrayMenu.put(
                                new JSONObject().put("ID", 2).put("Image", bitmapHelper.convertToBase64String(menuBitmap[1])).put("Name", "Okonomiyaki").put("Price", 32000).put("Description", "Okonomiyaki will be the best choice if it's raining. Because the sauce will make you awake. You will be amazed by the taste.")
                                        .put("Ingredients",
                                                new JSONArray()
                                                        .put(new JSONObject().put("ID", 8).put("Name", "Tomato Sauce"))
                                                        .put(new JSONObject().put("ID", 9).put("Name", "Cabbage"))
                                                        .put(new JSONObject().put("ID", 10).put("Name", "Flour"))
                                                        .put(new JSONObject().put("ID", 5).put("Name", "Egg"))
                                                        .put(new JSONObject().put("ID", 11).put("Name", "Mayonaise"))
                                                        .put(new JSONObject().put("ID", 12).put("Name", "Katsuobushi"))
                                        ).put("Details",
                                        new JSONArray()
                                                .put("350 gr of Cabbage")
                                                .put("100 gr of Flour")
                                                .put("120 gr of Katsuobushi")
                                                .put("2 pcs of Egg")
                                )
                        );
                        jsonArrayMenu.put(
                                new JSONObject().put("ID", 3).put("Image", bitmapHelper.convertToBase64String(menuBitmap[2])).put("Name", "Onigiri").put("Price", 15000).put("Description", "It's made for you who don't want to eat too much. With the vegetables in it, it will helps you on a diet. The crispy nori will make you smile all day long")
                                        .put("Ingredients",
                                                new JSONArray()
                                                        .put(new JSONObject().put("ID", 1).put("Name", "Rice"))
                                                        .put(new JSONObject().put("ID", 7).put("Name", "Nori"))
                                                        .put(new JSONObject().put("ID", 13).put("Name", "Salt"))
                                                        .put(new JSONObject().put("ID", 3).put("Name", "Tuna"))
                                                        .put(new JSONObject().put("ID", 14).put("Name", "Sesame"))
                                        ).put("Details",
                                        new JSONArray()
                                                .put("2 pcs of Onigiri")
                                                .put("150 gr of Rice each")
                                                .put("1 pc of Nori each")
                                )
                        );
                        jsonArrayMenu.put(
                                new JSONObject().put("ID", 4).put("Image", bitmapHelper.convertToBase64String(menuBitmap[3])).put("Name", "Ramen").put("Price", 27000).put("Description", "The best ramen is here. With our special hand-made noodle and cured egg. The broth used in this ramen is from a 1 years old chicken")
                                        .put("Ingredients",
                                                new JSONArray()
                                                        .put(new JSONObject().put("ID", 15).put("Name", "Noodle"))
                                                        .put(new JSONObject().put("ID", 16).put("Name", "Chicken"))
                                                        .put(new JSONObject().put("ID", 5).put("Name", "Egg"))
                                                        .put(new JSONObject().put("ID", 17).put("Name", "Soy Sauce"))
                                                        .put(new JSONObject().put("ID", 18).put("Name", "Garlic"))
                                                        .put(new JSONObject().put("ID", 19).put("Name", "Spring Onion"))
                                        ).put("Details",
                                        new JSONArray()
                                                .put("400 gr of Noodle")
                                                .put("100 gr of Chicken")
                                                .put("1 pc of Cured Egg")
                                )
                        );

                        sharedPref.edit()
                                .putString("Menus", jsonArrayMenu.toString())
                                .apply();

                        JSONArray jsonArrayIngredients = new JSONArray();
                        jsonArrayIngredients
                                .put(new JSONObject().put("ID", 1).put("Name", "Rice"))
                                .put(new JSONObject().put("ID", 2).put("Name", "Shrimp"))
                                .put(new JSONObject().put("ID", 3).put("Name", "Tuna"))
                                .put(new JSONObject().put("ID", 4).put("Name", "Salmon"))
                                .put(new JSONObject().put("ID", 5).put("Name", "Egg"))
                                .put(new JSONObject().put("ID", 6).put("Name", "Squid"))
                                .put(new JSONObject().put("ID", 7).put("Name", "Nori"))
                                .put(new JSONObject().put("ID", 8).put("Name", "Tomato Sauce"))
                                .put(new JSONObject().put("ID", 9).put("Name", "Cabbage"))
                                .put(new JSONObject().put("ID", 10).put("Name", "Flour"))
                                .put(new JSONObject().put("ID", 11).put("Name", "Mayonaise"))
                                .put(new JSONObject().put("ID", 12).put("Name", "Katsuobushi"))
                                .put(new JSONObject().put("ID", 13).put("Name", "Salt"))
                                .put(new JSONObject().put("ID", 14).put("Name", "Sesame"))
                                .put(new JSONObject().put("ID", 15).put("Name", "Noodle"))
                                .put(new JSONObject().put("ID", 16).put("Name", "Chicken"))
                                .put(new JSONObject().put("ID", 17).put("Name", "Soy Sauce"))
                                .put(new JSONObject().put("ID", 18).put("Name", "Garlic"))
                                .put(new JSONObject().put("ID", 19).put("Name", "Spring Onion"));

                        sharedPref.edit()
                                .putString("Ingredients", jsonArrayIngredients.toString())
                                .apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(LoadingActivity.this, SplashScreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Already used at least once

                    String loggedInUserEmail = sharedPref.getString("LoggedInUserEmail", "defaultValue");
                    if(!loggedInUserEmail.equals("defaultValue")) {
                        // Already logged in before

                        JSONObject objectUser = new UserController(LoadingActivity.this).getUserObjectByEmail(loggedInUserEmail);
                        try {
                            String userRole = objectUser.getString("Role");
                            if(userRole.equals("Admin")) {
                                Intent intent = new Intent(LoadingActivity.this, AdminMainActivity.class);
                                intent.putExtra("Email", loggedInUserEmail);
                                startActivity(intent);
                            } else if (userRole.equals("Cashier")) {
                                Intent intent = new Intent(LoadingActivity.this, CashierMainActivity.class);
                                intent.putExtra("Email", loggedInUserEmail);
                                startActivity(intent);
                            } else if(userRole.equals("Chef")) {
                                Intent intent = new Intent(LoadingActivity.this, ChefMainActivity.class);
                                intent.putExtra("Email", loggedInUserEmail);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(LoadingActivity.this, MemberMainActivity.class);
                                intent.putExtra("Email", loggedInUserEmail);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Never logged in before
                        Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    finish();
                }
            }
        }, 1800);
    }

    private void LoadAnimation() {
        lblHeader.animate().translationX(0).alpha(1).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator());
        lblDesc.animate().translationX(0).alpha(1).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator());
        imgRedMoon.animate().translationY(0).setDuration(600).setStartDelay(500).setInterpolator(new AccelerateDecelerateInterpolator());
    }
}