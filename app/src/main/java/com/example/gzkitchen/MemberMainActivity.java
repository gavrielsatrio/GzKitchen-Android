package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzkitchen.Controllers.MenuController;
import com.example.gzkitchen.Controllers.UserController;

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

    ImageView imgBackgroundOrnament;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100) {
            // Update Profile
            LoadDataMember();
        }
    }

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

        imgBackgroundOrnament = findViewById(R.id.memberMainImgBackgroundOrnament);

        LoadAnimation();
        LoadDataMember();

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberMainActivity.this, ProfileActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        try {
            JSONArray jsonArrayFoods = new MenuController(MemberMainActivity.this).getMenus();

            recViewRecommended.setAdapter(new MenuItemSmallAdapter(MemberMainActivity.this, jsonArrayFoods));
            recViewRecommended.setLayoutManager(new LinearLayoutManager(MemberMainActivity.this, LinearLayoutManager.HORIZONTAL, false));

            recViewPopular.setAdapter(new MenuItemSmallAdapter(MemberMainActivity.this, jsonArrayFoods));
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

    private void LoadDataMember() {
        try {
            JSONObject objectUser = new UserController(MemberMainActivity.this).getLoggedInUserObject();
            lblName.setText(objectUser.getString("Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void LoadAnimation() {
        imgBackgroundOrnament.animate().setDuration(800).alpha(1).translationY(0).setInterpolator(new DecelerateInterpolator());

        lblHello.animate().setDuration(600).setStartDelay(120).alpha(1).translationY(0);
        lblName.animate().setDuration(600).setStartDelay(240).alpha(1).translationX(0);
        btnProfile.animate().setDuration(1200).setStartDelay(360).alpha(1).translationY(0);

        lblDesc.animate().setDuration(600).setStartDelay(480).alpha(1).translationX(0);
        imgMemberMain.animate().setDuration(600).setStartDelay(600).alpha(1).translationX(0);

        lblRecommended.animate().setDuration(600).setStartDelay(720).alpha(1).translationX(0);
        lblPopular.animate().setDuration(600).setStartDelay(840).alpha(1).translationX(0);
        lblEmployee.animate().setDuration(600).setStartDelay(960).alpha(1).translationX(0);
    }
}