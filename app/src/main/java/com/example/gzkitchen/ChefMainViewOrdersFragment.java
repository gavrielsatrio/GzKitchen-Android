package com.example.gzkitchen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gzkitchen.Controllers.OrderController;
import com.example.gzkitchen.Helper.OrderIDHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ChefMainViewOrdersFragment extends Fragment {
    View viewInflate;
    ChefMainActivity chefMainActivity;
    ChefMainHomeFragment chefMainHomeFragment;
    OrderController orderController;

    LinearLayout linearLayoutOngoingOrders;

    public ChefMainViewOrdersFragment(ChefMainActivity chefMainActivityParam, ChefMainHomeFragment chefMainHomeFragmentParam) {
        this.chefMainActivity = chefMainActivityParam;
        this.chefMainHomeFragment = chefMainHomeFragmentParam;
        this.orderController = new OrderController(chefMainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.chef_main_view_orders_layout, container, false);

        linearLayoutOngoingOrders = viewInflate.findViewById(R.id.chefMainViewOrdersLinearLayoutAllOrders);
        LoadData();

        return viewInflate;
    }

    private void LoadData() {
        linearLayoutOngoingOrders.removeAllViews();

        JSONArray jsonArrayOngoingOrders = orderController.getOrderWhere("StatusID", "equals", "1");
        for(int i = 0; i < jsonArrayOngoingOrders.length(); i++) {
            try {
                JSONObject objectOrder = jsonArrayOngoingOrders.getJSONObject(i);
                View viewOngoingOrder = LayoutInflater.from(chefMainActivity).inflate(R.layout.view_orders_item_layout, null, false);

                String orderID = objectOrder.getString("ID");
                String orderIDDislay = new OrderIDHelper().getDisplayOrderID(orderID);

                CardView cardViewBackground = viewOngoingOrder.findViewById(R.id.viewOrdersItemLayoutCardViewBackground);

                ((TextView)viewOngoingOrder.findViewById(R.id.viewOrdersItemLayoutLblOrderID)).setText(orderIDDislay);
                ((TextView)viewOngoingOrder.findViewById(R.id.viewOrdersItemLayoutLblTableNoValue)).setText(objectOrder.getString("TableNo"));
                ((TextView)viewOngoingOrder.findViewById(R.id.viewOrdersItemLayoutLblMenuCount)).setText("Menu : " + objectOrder.getJSONArray("OrderedMenus").length() + " item(s)");
                ((Button)viewOngoingOrder.findViewById(R.id.viewOrdersItemLayoutBtnStartCook)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderController.updateOrderStatus(orderID, 2);
                        cardViewBackground.animate().translationX(200).alpha(0).setDuration(800).setInterpolator(new AccelerateDecelerateInterpolator());

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                chefMainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadData();
                                        chefMainActivity.LoadSelectedTab(0);
                                        chefMainHomeFragment.LoadDataCooking();
                                    }
                                });
                            }
                        }, 800);
                    }
                });

                linearLayoutOngoingOrders.addView(viewOngoingOrder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
