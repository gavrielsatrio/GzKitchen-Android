package com.example.gzkitchen;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

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

    JSONArray jsonArraySort;

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

        LoadAnimation();
        LoadComboSort();
        LoadDataCashier();
        LoadData();

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cashierMainActivity, ProfileActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        comboSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return viewInflate;
    }

    private void LoadAnimation() {
        imgTop.animate().setDuration(600).translationY(0).alpha(1);
        lblHello.animate().setDuration(600).setStartDelay(120).translationX(0).alpha(1);
        lblName.animate().setDuration(600).setStartDelay(240).translationY(0).alpha(1);
        lblRole.animate().setDuration(600).setStartDelay(360).translationX(0).alpha(1);
        btnProfile.animate().setDuration(600).setStartDelay(480).translationY(0).alpha(1);
        comboSort.animate().setDuration(600).setStartDelay(600).translationY(0).alpha(1);
    }

    private void LoadComboSort() {
        try {
            jsonArraySort = new JSONArray()
                    .put(new JSONObject().put("Name", "Date"))
                    .put(new JSONObject().put("Name", "Status"));
            comboSort.setAdapter(new ComboBoxAdapter(cashierMainActivity, jsonArraySort));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void LoadData() {
        linearLayoutOngoingOrders.removeAllViews();

        try {
            JSONArray jsonArrayOrder = orderController.getOrders();
            ArrayList<JSONObject> arrayListOrder = new ArrayList<>();

            for(int i = 0; i < jsonArrayOrder.length(); i++) {
                arrayListOrder.add(jsonArrayOrder.getJSONObject(i));
            }

            String sortBy = jsonArraySort.getString(comboSort.getSelectedItemPosition());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                arrayListOrder.sort(new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject jsonObject, JSONObject jsonObject2) {
                        int comp = 0;
                        try {
                            if(sortBy.equals("Date")) {
                                if((((Date)jsonObject.get("Date")).after((Date)jsonObject2.get("Date")))) {
                                    comp = -1;
                                } else {
                                    comp = 1;
                                }
                            } else {
                                if(jsonObject.getInt("StatusID") > jsonObject2.getInt("StatusID")) {
                                    comp = -1;
                                } else {
                                    comp = 1;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return comp;
                    }
                });
            }

            Log.d("List", jsonArrayOrder.toString());

            for(int i = 0; i < jsonArrayOrder.length(); i++) {
                JSONObject objectOrder = arrayListOrder.get(i);
                View viewOngoingOrder = LayoutInflater.from(cashierMainActivity).inflate(R.layout.ongoing_order_layout, null, false);

                linearLayoutOngoingOrders.addView(viewOngoingOrder);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
