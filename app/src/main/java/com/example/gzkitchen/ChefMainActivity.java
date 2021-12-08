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

    ViewPager2 viewPager;
    LinearLayout btnHome;
    ImageView btnHomeImg;
    TextView btnHomeLbl;
    LinearLayout btnTakeOrder;
    ImageView btnTakeOrderImg;
    TextView btnTakeOrderLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_main);

        viewPager = findViewById(R.id.chefMainViewPager);
        btnHome = findViewById(R.id.chefMainBtnHome);
        btnHomeImg = findViewById(R.id.chefMainImgHome);
        btnHomeLbl = findViewById(R.id.chefMainLblHome);
        btnTakeOrder = findViewById(R.id.chefMainBtnTakeOrder);
        btnTakeOrderImg = findViewById(R.id.chefMainImgTakeOrder);
        btnTakeOrderLbl = findViewById(R.id.chefMainLblTakeOrder);


        JSONArray jsonArrayLayout = new JSONArray()
                .put(new ChefMainHomeFragment(ChefMainActivity.this))
                .put(new ChefMainTakeOrderFragment(ChefMainActivity.this));

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

        btnTakeOrderImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadSelectedTab(1);
            }
        });

        LoadSelectedTab(0);
    }

    private void LoadSelectedTab(int selectedPosition) {
        btnHomeImg.setColorFilter(null);
        btnTakeOrderImg.setColorFilter(null);

        btnHomeLbl.setVisibility(View.VISIBLE);
        btnTakeOrderLbl.setVisibility(View.VISIBLE);

        if(selectedPosition == 0) {
            btnTakeOrderLbl.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                btnTakeOrderImg.setColorFilter(getColor(R.color.colorLightGrey));
            } else {
                btnTakeOrderImg.setColorFilter(getResources().getColor(R.color.colorLightGrey));
            }
        } else {
            btnHomeLbl.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                btnHomeImg.setColorFilter(getColor(R.color.colorLightGrey));
            } else {
                btnHomeImg.setColorFilter(getResources().getColor(R.color.colorLightGrey));
            }
        }

        viewPager.setCurrentItem(selectedPosition, true);
    }
}