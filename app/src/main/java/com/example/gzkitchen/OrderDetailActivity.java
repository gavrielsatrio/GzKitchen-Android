package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {

    TextView btnBack;
    TextView lblOrderIDValue;
    TextView lblTableNoValue;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        btnBack = findViewById(R.id.orderDetailBtnBack);
        lblOrderIDValue = findViewById(R.id.orderDetailLblOrderIDValue);
        lblTableNoValue = findViewById(R.id.orderDetailLblTableNoValue);
        listView = findViewById(R.id.orderDetailListView);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailActivity.super.onBackPressed();
            }
        });

        lblOrderIDValue.setText(getIntent().getStringExtra("OrderID"));
        lblTableNoValue.setText(getIntent().getStringExtra("TableNo"));
    }
}