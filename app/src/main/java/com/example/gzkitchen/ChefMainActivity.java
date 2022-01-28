package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

public class ChefMainActivity extends AppCompatActivity {

    LinearLayout btnHome;
    ImageView btnHomeImg;
    TextView btnHomeLbl;
    LinearLayout btnViewOrders;
    ImageView btnViewOrdersImg;
    TextView btnViewOrdersLbl;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_main);

        btnHome = findViewById(R.id.chefMainBtnHome);
        btnHomeImg = findViewById(R.id.chefMainImgHome);
        btnHomeLbl = findViewById(R.id.chefMainLblHome);
        btnViewOrders = findViewById(R.id.chefMainBtnViewOrders);
        btnViewOrdersImg = findViewById(R.id.chefMainImgViewOrders);
        btnViewOrdersLbl = findViewById(R.id.chefMainLblViewOrders);
        viewPager = findViewById(R.id.chefMainViewPager);

        JSONArray jsonArrayLayout = new JSONArray()
                .put(new ChefMainHomeFragment(ChefMainActivity.this))
                .put(new ChefMainViewOrdersFragment(ChefMainActivity.this));

        viewPager.setAdapter(new ChefMainPagerAdapter(ChefMainActivity.this, jsonArrayLayout));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                LoadSelectedTab(position);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadSelectedTab(0);
            }
        });

        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadSelectedTab(1);
            }
        });
    }

    private void LoadSelectedTab(int position) {
        btnHomeImg.setColorFilter(null);
        btnViewOrdersImg.setColorFilter(null);

        btnHomeLbl.setVisibility(View.VISIBLE);
        btnViewOrdersLbl.setVisibility(View.VISIBLE);

        if(position == 0) {
            btnViewOrdersLbl.setVisibility(View.GONE);
            btnViewOrdersImg.setColorFilter(getResources().getColor(R.color.colorLightGrey));
        } else {
            btnHomeLbl.setVisibility(View.GONE);
            btnHomeImg.setColorFilter(getResources().getColor(R.color.colorLightGrey));
        }

        viewPager.setCurrentItem(position, true);
    }
}