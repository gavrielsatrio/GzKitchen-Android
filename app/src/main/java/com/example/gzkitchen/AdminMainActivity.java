package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdminMainActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    LinearLayout btnHome;
    TextView lblHome;
    ImageView imgHome;
    LinearLayout btnMenu;
    TextView lblMenu;
    ImageView imgMenu;
    LinearLayout btnUsers;
    TextView lblUsers;
    ImageView imgUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        viewPager = findViewById(R.id.adminMainViewPager);
        btnHome = findViewById(R.id.adminMainBtnHome);
        lblHome = findViewById(R.id.adminMainLblHome);
        imgHome = findViewById(R.id.adminMainImgHome);

        btnMenu = findViewById(R.id.adminMainBtnMenu);
        lblMenu = findViewById(R.id.adminMainLblMenu);
        imgMenu = findViewById(R.id.adminMainImgMenu);

        btnUsers = findViewById(R.id.adminMainBtnUsers);
        lblUsers = findViewById(R.id.adminMainLblUsers);
        imgUsers = findViewById(R.id.adminMainImgUsers);

        JSONArray jsonArrayLayout = new JSONArray()
                .put(new AdminMainHomeFragment(AdminMainActivity.this))
                .put(new AdminMainMenuFragment(AdminMainActivity.this))
                .put(new AdminMainUsersFragment(AdminMainActivity.this));
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
        imgHome.setColorFilter(null);
        imgMenu.setColorFilter(null);
        imgUsers.setColorFilter(null);

        lblHome.setVisibility(View.VISIBLE);
        lblMenu.setVisibility(View.VISIBLE);
        lblUsers.setVisibility(View.VISIBLE);

        if(positionSelected == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imgMenu.setColorFilter(getColor(R.color.colorLightGrey));
                imgUsers.setColorFilter(getColor(R.color.colorLightGrey));
            } else {
                imgMenu.setColorFilter(getResources().getColor(R.color.colorLightGrey));
                imgUsers.setColorFilter(getResources().getColor(R.color.colorLightGrey));
            }

            lblMenu.setVisibility(View.GONE);
            lblUsers.setVisibility(View.GONE);
        } else if(positionSelected == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imgHome.setColorFilter(getColor(R.color.colorLightGrey));
                imgUsers.setColorFilter(getColor(R.color.colorLightGrey));
            } else {
                imgHome.setColorFilter(getResources().getColor(R.color.colorLightGrey));
                imgUsers.setColorFilter(getResources().getColor(R.color.colorLightGrey));
            }

            lblHome.setVisibility(View.GONE);
            lblUsers.setVisibility(View.GONE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imgHome.setColorFilter(getColor(R.color.colorLightGrey));
                imgMenu.setColorFilter(getColor(R.color.colorLightGrey));
            } else {
                imgHome.setColorFilter(getResources().getColor(R.color.colorLightGrey));
                imgMenu.setColorFilter(getResources().getColor(R.color.colorLightGrey));
            }

            lblHome.setVisibility(View.GONE);
            lblMenu.setVisibility(View.GONE);
        }

        viewPager.setCurrentItem(positionSelected, true);
    }
}