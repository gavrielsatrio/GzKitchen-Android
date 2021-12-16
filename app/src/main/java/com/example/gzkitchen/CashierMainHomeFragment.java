package com.example.gzkitchen;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CashierMainHomeFragment extends Fragment {
    View viewInflate;
    CashierMainActivity cashierMainActivity;
    OrderController orderController;

    ImageView imgTop;
    TextView lblHello;
    TextView lblName;
    TextView lblRole;
    ImageView btnProfile;
    Spinner comboSort;
    LinearLayout linearLayoutOngoingOrders;

    public CashierMainHomeFragment(CashierMainActivity cashierMainActivityParam) {
        this.cashierMainActivity = cashierMainActivityParam;
        this.orderController = new OrderController(cashierMainActivity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100) {
            LoadDataCashier();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.cashier_main_home_layout, container, false);

        imgTop = viewInflate.findViewById(R.id.cashierMainHomeImgTop);
        lblHello = viewInflate.findViewById(R.id.cashierMainHomeLblHello);
        lblName = viewInflate.findViewById(R.id.cashierMainHomeLblName);
        lblRole = viewInflate.findViewById(R.id.cashierMainHomeLblRole);
        btnProfile = viewInflate.findViewById(R.id.cashierMainHomeBtnProfile);
        comboSort = viewInflate.findViewById(R.id.cashierMainHomeComboSort);
        linearLayoutOngoingOrders = viewInflate.findViewById(R.id.cashierMainHomeLinearLayoutOngoingOrders);

        LoadDataCashier();
        LoadData();

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cashierMainActivity, ProfileActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        return viewInflate;
    }

    private void LoadData() {
        JSONArray jsonArrayOrder = orderController.getOrders();

        for(int i = 0; i < jsonArrayOrder.length(); i++) {
            View viewOngoingOrder = LayoutInflater.from(cashierMainActivity).inflate(R.layout.ongoing_order_layout, null, false);

            linearLayoutOngoingOrders.addView(viewOngoingOrder);
        }
    }

    private void LoadDataCashier() {
        UserController userController = new UserController(cashierMainActivity);
        JSONObject objectUser = userController.getLoggedInUserObject();

        try {
            lblName.setText(objectUser.getString("Name"));
            lblRole.setText(objectUser.getString("Role"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
