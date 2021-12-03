package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MemberMainActivity extends AppCompatActivity {

    TextView lblHello;
    TextView lblName;
    TextView lblDesc;
    ImageView imgMemberMain;
    ImageView btnProfile;

    TextView lblRecommended;
    RecyclerView recViewRecommended;

    TextView lblPopular;
    RecyclerView recViewPopular;

    TextView lblEmployee;
    RecyclerView recViewEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_main);

        lblHello = findViewById(R.id.memberMainLblHello);
        lblName = findViewById(R.id.memberMainLblName);
        lblDesc = findViewById(R.id.memberMainLblDesc);
        imgMemberMain = findViewById(R.id.memberMainImg);
        btnProfile = findViewById(R.id.memberMainBtnProfile);

        lblRecommended = findViewById(R.id.memberMainLblRecommendedItems);
        recViewRecommended = findViewById(R.id.memberMainRecViewRecommendedItems);

        lblPopular = findViewById(R.id.memberMainLblPopularItems);
        recViewPopular = findViewById(R.id.memberMainRecViewMostPopularItems);

        lblEmployee = findViewById(R.id.memberMainLblEmployeeOfTheMonth);
        recViewEmployee = findViewById(R.id.memberMainRecViewEmployeeOfTheMonth);

        lblName.setText("GZ Ríō");

        LoadAnimation();

        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(
                    new JSONObject().put("Image", R.drawable.sushi2).put("Name", "Sushi").put("Price", 20000).put("Description", "This sushi is specially made for you by the chef. It uses the best quality of salmon dan tuna. The nori is still crispy and delicious")
                    .put("Ingredients",
                            new JSONArray()
                                    .put(new JSONObject().put("Name", "Rice"))
                                    .put(new JSONObject().put("Name", "Shrimp"))
                                    .put(new JSONObject().put("Name", "Tuna"))
                                    .put(new JSONObject().put("Name", "Salmon"))
                                    .put(new JSONObject().put("Name", "Egg"))
                                    .put(new JSONObject().put("Name", "Squid"))
                                    .put(new JSONObject().put("Name", "Nori"))
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
            jsonArray.put(
                    new JSONObject().put("Image", R.drawable.okonomiyaki).put("Name", "Okonomiyaki").put("Price", 32000).put("Description", "Okonomiyaki will be the best choice if it's raining. Because the sauce will make you awake. You will be amazed by the taste.")
                    .put("Ingredients",
                            new JSONArray()
                                    .put(new JSONObject().put("Name", "Sauce"))
                                    .put(new JSONObject().put("Name", "Cabbage"))
                                    .put(new JSONObject().put("Name", "Flour"))
                                    .put(new JSONObject().put("Name", "Egg"))
                                    .put(new JSONObject().put("Name", "Mayonaise"))
                                    .put(new JSONObject().put("Name", "Katsuobushi"))
                    ).put("Details",
                            new JSONArray()
                                    .put("350 gr of Cabbage")
                                    .put("100 gr of Flour")
                                    .put("120 gr of Katsuobushi")
                                    .put("2 pcs of Egg")
                    )
            );
            jsonArray.put(
                    new JSONObject().put("Image", R.drawable.onigiri).put("Name", "Onigiri").put("Price", 15000).put("Description", "It's made for you who don't want to eat too much. With the vegetables in it, it will helps you on a diet. The crispy nori will make you smile all day long")
                    .put("Ingredients",
                            new JSONArray()
                                    .put(new JSONObject().put("Name", "Rice"))
                                    .put(new JSONObject().put("Name", "Nori"))
                                    .put(new JSONObject().put("Name", "Salt"))
                                    .put(new JSONObject().put("Name", "Tuna"))
                                    .put(new JSONObject().put("Name", "Sesame"))
                    ).put("Details",
                            new JSONArray()
                                    .put("2 pcs of Onigiri")
                                    .put("150 gr of Rice each")
                                    .put("1 pc of Nori each")
                    )
            );
            jsonArray.put(
                    new JSONObject().put("Image", R.drawable.ramen).put("Name", "Ramen").put("Price", 27000).put("Description", "The best ramen is here. With our special hand-made noodle and cured egg. The broth used in this ramen is from a 1 years old chicken")
                    .put("Ingredients",
                            new JSONArray()
                                    .put(new JSONObject().put("Name", "Noodle"))
                                    .put(new JSONObject().put("Name", "Chicken"))
                                    .put(new JSONObject().put("Name", "Egg"))
                                    .put(new JSONObject().put("Name", "Soy Sauce"))
                                    .put(new JSONObject().put("Name", "Garlic"))
                                    .put(new JSONObject().put("Name", "Spring Onion"))
                    ).put("Details",
                            new JSONArray()
                                    .put("400 gr of Noodle")
                                    .put("100 gr of Chicken")
                                    .put("1 pc of Cured Egg")
                    )
            );

            recViewRecommended.setAdapter(new FoodItemSmallAdapter(MemberMainActivity.this, jsonArray));
            recViewRecommended.setLayoutManager(new LinearLayoutManager(MemberMainActivity.this, LinearLayoutManager.HORIZONTAL, false));

            recViewPopular.setAdapter(new FoodItemSmallAdapter(MemberMainActivity.this, jsonArray));
            recViewPopular.setLayoutManager(new LinearLayoutManager(MemberMainActivity.this, LinearLayoutManager.HORIZONTAL, false));

            JSONArray jsonArrayEmployee = new JSONArray();
            jsonArrayEmployee.put(new JSONObject().put("Name", "Gavriel").put("Month", "October 2021"));
            jsonArrayEmployee.put(new JSONObject().put("Name", "Satrio").put("Month", "November 2021"));
            jsonArrayEmployee.put(new JSONObject().put("Name", "Widjaya").put("Month", "December 2021"));

            recViewEmployee.setAdapter(new EmployeeOfTheMonthAdapter(MemberMainActivity.this, jsonArrayEmployee));
            recViewEmployee.setLayoutManager(new LinearLayoutManager(MemberMainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void LoadAnimation() {
        lblHello.animate().setDuration(600).alpha(1).translationY(0);
        lblName.animate().setDuration(600).setStartDelay(120).alpha(1).translationX(0);
        btnProfile.animate().setDuration(1200).setStartDelay(240).alpha(1).translationY(0);

        lblDesc.animate().setDuration(600).setStartDelay(360).alpha(1).translationX(0);
        imgMemberMain.animate().setDuration(600).setStartDelay(480).alpha(1).translationX(0);

        lblRecommended.animate().setDuration(600).setStartDelay(600).alpha(1).translationX(0);
        lblPopular.animate().setDuration(600).setStartDelay(720).alpha(1).translationX(0);
        lblEmployee.animate().setDuration(600).setStartDelay(840).alpha(1).translationX(0);
    }
}