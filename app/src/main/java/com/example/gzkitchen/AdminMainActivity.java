package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;

import org.json.JSONArray;

public class AdminMainActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    LinearLayout btnHome;
    TextView lblHome;
    LinearLayout btnMenu;
    TextView lblMenu;
    LinearLayout btnUsers;
    TextView lblUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        viewPager = findViewById(R.id.adminMainViewPager);
        btnHome = findViewById(R.id.adminMainBtnHome);
        lblHome = findViewById(R.id.adminMainLblHome);
        btnMenu = findViewById(R.id.adminMainBtnMenu);
        lblMenu = findViewById(R.id.adminMainLblMenu);
        btnUsers = findViewById(R.id.adminMainBtnUsers);
        lblUsers = findViewById(R.id.adminMainLblUsers);

        JSONArray jsonArrayLayout = new JSONArray()
                .put(new AdminMainHomeFragment())
                .put(new AdminMainMenuFragment())
                .put(new AdminMainUsersFragment());
        viewPager.setAdapter(new AdminMainPagerAdapter(AdminMainActivity.this, jsonArrayLayout));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                LoadSelectedTab(position);
            }
        });

        LoadSelectedTab(0);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadSelectedTab(0);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadSelectedTab(1);
            }
        });

        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadSelectedTab(2);
            }
        });
    }

    private void LoadSelectedTab(int positionSelected) {
        lblHome.setVisibility(View.VISIBLE);
        lblMenu.setVisibility(View.VISIBLE);
        lblUsers.setVisibility(View.VISIBLE);

        if(positionSelected == 0) {
            lblMenu.setVisibility(View.GONE);
            lblUsers.setVisibility(View.GONE);
        } else if(positionSelected == 1) {
            lblHome.setVisibility(View.GONE);
            lblUsers.setVisibility(View.GONE);
        } else {
            lblHome.setVisibility(View.GONE);
            lblMenu.setVisibility(View.GONE);
        }

        viewPager.setCurrentItem(positionSelected, true);
    }
}