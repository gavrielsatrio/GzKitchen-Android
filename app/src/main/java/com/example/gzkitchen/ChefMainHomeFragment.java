package com.example.gzkitchen;

import android.animation.TimeInterpolator;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gzkitchen.Controllers.MenuController;
import com.example.gzkitchen.Controllers.OrderController;
import com.example.gzkitchen.Controllers.OrderedMenuController;
import com.example.gzkitchen.Controllers.UserController;
import com.example.gzkitchen.Helper.OrderIDHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ChefMainHomeFragment extends Fragment {
    View viewInflate;
    ChefMainActivity chefMainActivity;
    UserController userController;
    OrderController orderController;
    MenuController menuController;

    TextView lblName;
    TextView lblRole;
    ImageView btnProfile;
    LinearLayout linearLayoutCurrentlyCooking;

    public ChefMainHomeFragment(ChefMainActivity chefMainActivityParam) {
        this.chefMainActivity = chefMainActivityParam;
        this.userController = new UserController(chefMainActivity);
        this.orderController = new OrderController(chefMainActivity);
        this.menuController = new MenuController(chefMainActivity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100) {
            LoadDataChef();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.chef_main_home_layout, container, false);

        lblName = viewInflate.findViewById(R.id.chefMainHomeLblName);
        lblRole = viewInflate.findViewById(R.id.chefMainHomeLblRole);
        btnProfile = viewInflate.findViewById(R.id.chefMainHomeBtnProfile);
        linearLayoutCurrentlyCooking = viewInflate.findViewById(R.id.chefMainHomeLinearLayoutCurrentlyCooking);

        LoadDataChef();
        LoadDataCooking();

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chefMainActivity, ProfileActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        return viewInflate;
    }

    public void LoadDataCooking() {
        linearLayoutCurrentlyCooking.removeAllViews();
        JSONArray jsonArrayOnCookingOrder = orderController.getOrderWhere("StatusID", "equals", "2");

        for(int i = 0; i < jsonArrayOnCookingOrder.length(); i++) {
            try {
                JSONObject objectOrder = jsonArrayOnCookingOrder.getJSONObject(i);
                View viewOnCookingOrder = LayoutInflater.from(chefMainActivity).inflate(R.layout.on_cooking_order_layout, null, false);

                String orderID = objectOrder.getString("ID");
                String orderIDDisplay = new OrderIDHelper().getDisplayOrderID(orderID);

                CardView cardViewBackground = (CardView) viewOnCookingOrder.findViewById(R.id.onCookingOrderLayoutCardViewBackground);
                ((TextView)viewOnCookingOrder.findViewById(R.id.onCookingOrderLayoutLblOrderID)).setText(orderIDDisplay);
                ((TextView)viewOnCookingOrder.findViewById(R.id.onCookingOrderLayoutLblTableNoValue)).setText(objectOrder.getString("TableNo"));

                Button btnFinishCook = (Button)viewOnCookingOrder.findViewById(R.id.onCookingOrderLayoutBtnFinish);
                btnFinishCook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderController.updateOrderStatus(orderID, 3);
                        cardViewBackground.animate().translationX(200).alpha(0).setDuration(800).setInterpolator(new AccelerateDecelerateInterpolator());

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                chefMainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadDataCooking();
                                    }
                                });
                            }
                        }, 800);
                    }
                });

                LinearLayout onCookingOrderLinearLayoutMenu = viewOnCookingOrder.findViewById(R.id.onCookingOrderLayoutLinearLayoutMenu);
                JSONArray jsonArrayOnCookingMenu = objectOrder.getJSONArray("OrderedMenus");
                for(int j = 0; j < jsonArrayOnCookingMenu.length(); j++) {
                    JSONObject objectMenu = jsonArrayOnCookingMenu.getJSONObject(j);
                    View viewOnCookingMenu = LayoutInflater.from(chefMainActivity).inflate(R.layout.on_cooking_menu_layout, null, false);

                    String menuID = objectMenu.getString("MenuID");
                    String menuName = menuController.getMenusWhere("ID", menuID).getJSONObject(0).getString("Name");

                    CheckBox checkBoxMenu = (CheckBox)viewOnCookingMenu.findViewById(R.id.onCookingMenuLayoutCheckBox);
                    checkBoxMenu.setText(objectMenu.getString("Qty") + " pcs of " + menuName);
                    checkBoxMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                            orderController.updateOrderMenuCookingStatus(orderID, menuID, checked);
                            if(checked) {
                                checkBoxMenu.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            } else {
                                checkBoxMenu.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                            }

                            LoadBtnFinishCookState(btnFinishCook, orderID);
                        }
                    });

                    onCookingOrderLinearLayoutMenu.addView(viewOnCookingMenu);
                }

                LoadBtnFinishCookState(btnFinishCook, orderID);
                linearLayoutCurrentlyCooking.addView(viewOnCookingOrder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void LoadBtnFinishCookState(Button btnFinishCook, String orderID) {
        try {
            JSONObject objectOrder = orderController.getOrderWhere("ID", "equals", orderID).getJSONObject(0);
            JSONArray jsonArrayMenu = objectOrder.getJSONArray("OrderedMenus");

            boolean isAllCooked = true;
            for(int i = 0; i < jsonArrayMenu.length(); i++) {
                if(!jsonArrayMenu.getJSONObject(i).getBoolean("IsCookDone")) {
                    isAllCooked = false;
                    break;
                }
            }

            if(isAllCooked) {
                btnFinishCook.setEnabled(true);
                btnFinishCook.getBackground().setColorFilter(null);
            } else {
                btnFinishCook.setEnabled(false);
                btnFinishCook.getBackground().setColorFilter(getResources().getColor(R.color.colorGrey), PorterDuff.Mode.SCREEN);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void LoadDataChef() {
        JSONObject objectUser = userController.getLoggedInUserObject();

        try {
            lblName.setText(objectUser.getString("Name"));
            lblRole.setText(objectUser.getString("Role"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
