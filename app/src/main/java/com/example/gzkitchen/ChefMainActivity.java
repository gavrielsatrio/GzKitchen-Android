package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ChefMainActivity extends AppCompatActivity {

    LinearLayout btnHome;
    LinearLayout btnViewOrder;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_main);

        btnHome = findViewById(R.id.chefMainBtnHome);
        btnViewOrder = findViewById(R.id.chefMainBtnViewOrder);
        viewPager = findViewById(R.id.chefMainViewPager);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void LoadSelectedTab(int position) {

    }
}