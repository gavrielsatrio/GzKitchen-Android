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

public class CashierMainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_cashier_main);

        viewPager = findViewById(R.id.cashierMainViewPager);
        btnHome = findViewById(R.id.cashierMainBtnHome);
        btnHomeImg = findViewById(R.id.cashierMainImgHome);
        btnHomeLbl = findViewById(R.id.cashierMainLblHome);
        btnTakeOrder = findViewById(R.id.cashierMainBtnTakeOrder);
        btnTakeOrderImg = findViewById(R.id.cashierMainImgTakeOrder);
        btnTakeOrderLbl = findViewById(R.id.cashierMainLblTakeOrder);


        JSONArray jsonArrayLayout = new JSONArray()
                .put(new CashierMainHomeFragment(CashierMainActivity.this))
                .put(new CashierMainTakeOrderFragment(CashierMainActivity.this));

        viewPager.setAdapter(new CashierMainPagerAdapter(CashierMainActivity.this, jsonArrayLayout));
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

        btnTakeOrder.setOnClickListener(new View.OnClickListener() {
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