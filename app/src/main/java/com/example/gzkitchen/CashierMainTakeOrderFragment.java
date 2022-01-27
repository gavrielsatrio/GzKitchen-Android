package com.example.gzkitchen;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gzkitchen.Controllers.MenuController;
import com.example.gzkitchen.Controllers.OrderController;
import com.example.gzkitchen.Controllers.OrderedMenuController;
import com.example.gzkitchen.Helper.BitmapHelper;
import com.example.gzkitchen.Helper.DateAndTimeHelper;
import com.example.gzkitchen.Helper.PriceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class CashierMainTakeOrderFragment extends Fragment {
    CashierMainActivity cashierMainActivity;
    CashierMainHomeFragment cashierHomeFragment;
    View viewInflate;
    OrderController orderController;
    MenuController menuController;
    PriceHelper priceHelper = new PriceHelper();
    OrderedMenuController orderedMenuController = new OrderedMenuController();

    TextView lblHeader;
    EditText txtSearch;
    ImageView btnSearch;
    LinearLayout linearLayoutOrderMenuList;
    CardView cardViewBottom;
    TextView lblTotalPrice;
    Button btnProceedOrder;

    JSONArray jsonArrayOrderedMenu = new JSONArray();

    public CashierMainTakeOrderFragment(CashierMainActivity cashierMainActivityParam, CashierMainHomeFragment cashierHomeFragmentParam) {
        this.cashierMainActivity = cashierMainActivityParam;
        this.cashierHomeFragment = cashierHomeFragmentParam;
        orderController = new OrderController(cashierMainActivity);
        menuController = new MenuController(cashierMainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.cashier_main_take_order_layout, container, false);

        lblHeader = viewInflate.findViewById(R.id.cashierMainTakeOrderLblHeader);
        txtSearch = viewInflate.findViewById(R.id.cashierMainTakeOrderTxtSearch);
        btnSearch = viewInflate.findViewById(R.id.cashierMainTakeOrderBtnSearch);
        linearLayoutOrderMenuList = viewInflate.findViewById(R.id.cashierMainTakeOrderLinearLayoutOrderMenuList);
        cardViewBottom = viewInflate.findViewById(R.id.cashierMainTakeOrderCardViewBottom);
        lblTotalPrice = viewInflate.findViewById(R.id.cashierMainTakeOrderLblTotalPrice);
        btnProceedOrder = viewInflate.findViewById(R.id.cashierMainTakeOrderBtnProceedOrder);

        LoadData();
        LoadAnimation();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData();
            }
        });

        btnProceedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jsonArrayOrderedMenu.length() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(cashierMainActivity);
                    AlertDialog dialog = builder.create();

                    View viewDialog = LayoutInflater.from(cashierMainActivity).inflate(R.layout.proceed_order_confirmation_popup_layout, null, false);
                    dialog.setView(viewDialog);
                    dialog.setCancelable(true);

                    EditText txtTableNo = viewDialog.findViewById(R.id.proceedOrderConfirmationPopupLayoutTxtTableNo);
                    LinearLayout linearLayoutOrderedMenu = viewDialog.findViewById(R.id.proceedOrderConfirmationPopupLayoutLinearLayoutOrderedMenuList);
                    for(int i = 0; i < jsonArrayOrderedMenu.length(); i++) {
                        View viewOrderedMenu = LayoutInflater.from(cashierMainActivity).inflate(R.layout.ordered_menu_layout, null, false);

                        try {
                            JSONObject objectOrderedMenu = jsonArrayOrderedMenu.getJSONObject(i);

                            int price = objectOrderedMenu.getInt("Price");
                            int qty = objectOrderedMenu.getInt("Qty");
                            int subtotal = qty * price;

                            ((TextView)viewOrderedMenu.findViewById(R.id.orderedMenuLayoutLblName)).setText(menuController.getMenusWhere("ID", objectOrderedMenu.getString("MenuID")).getJSONObject(0).getString("Name"));
                            ((TextView)viewOrderedMenu.findViewById(R.id.orderedMenuLayoutLblPrice)).setText(priceHelper.convertToRupiah(price));
                            ((TextView)viewOrderedMenu.findViewById(R.id.orderedMenuLayoutLblQty)).setText("x" + qty);
                            ((TextView)viewOrderedMenu.findViewById(R.id.orderedMenuLayoutLblSubtotal)).setText(priceHelper.convertToRupiah(subtotal));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        linearLayoutOrderedMenu.addView(viewOrderedMenu);
                    }

                    ((TextView)viewDialog.findViewById(R.id.proceedOrderConfirmationPopupLayoutLblTotalPrice)).setText("Total Charge : " + lblTotalPrice.getText().toString());
                    ((Button)viewDialog.findViewById(R.id.proceedOrderConfirmationPopupLayoutBtnCancel)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    ((Button)viewDialog.findViewById(R.id.proceedOrderConfirmationPopupLayoutBtnConfirm)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!txtTableNo.getText().toString().trim().equals("")) {
                                try {
                                    JSONObject objectOrder = new JSONObject();
                                    objectOrder.put("ID", orderController.getLastOrderID() + 1);
                                    objectOrder.put("Date", new DateAndTimeHelper().ConvertToString(Calendar.getInstance().getTime()));
                                    objectOrder.put("TableNo", txtTableNo.getText().toString().trim());
                                    objectOrder.put("StatusID", 1);
                                    objectOrder.put("OrderedMenus", jsonArrayOrderedMenu);

                                    orderController.addOrder(objectOrder);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                jsonArrayOrderedMenu = new JSONArray();
                                LoadData();
                                LoadTotalPrice();

                                cashierMainActivity.LoadSelectedTab(0);
                                cashierHomeFragment.LoadData();

                                dialog.dismiss();
                            } else {
                                Toast.makeText(cashierMainActivity, "Fill up table no", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    dialog.show();
                } else {
                    Toast.makeText(cashierMainActivity, "Add at least 1 menu to proceed order", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return viewInflate;
    }

    private void LoadAnimation() {
        lblHeader.animate().setDuration(600).alpha(1).translationY(0);
        txtSearch.animate().setDuration(600).setStartDelay(120).alpha(1).translationX(0);
        btnSearch.animate().setDuration(600).setStartDelay(240).alpha(1).translationX(0);
        cardViewBottom.animate().setDuration(800).setStartDelay(360).alpha(1).translationY(0);
        lblTotalPrice.animate().setDuration(600).setStartDelay(480).alpha(1).translationX(0);
        btnProceedOrder.animate().setDuration(600).setStartDelay(480).alpha(1).translationX(0);
    }

    private void LoadData() {
        linearLayoutOrderMenuList.removeAllViews();

        JSONArray jsonArrayMenuList;
        if(txtSearch.getText().toString().trim().equals("")) {
            jsonArrayMenuList = menuController.getMenus();
        } else {
            jsonArrayMenuList = menuController.getMenusWhere("Name", txtSearch.getText().toString().trim());
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cashierMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < jsonArrayMenuList.length(); i++) {
                            try {
                                JSONObject objectMenu = jsonArrayMenuList.getJSONObject(i);
                                View viewMenuList = LayoutInflater.from(cashierMainActivity).inflate(R.layout.take_order_menu_layout, null, false);

                                int menuID = objectMenu.getInt("ID");
                                int price = objectMenu.getInt("Price");

                                ((ImageView)viewMenuList.findViewById(R.id.takeOrderLayoutImg)).setImageBitmap(new BitmapHelper().convertToBitmap(objectMenu.getString("Image")));
                                ((TextView)viewMenuList.findViewById(R.id.takeOrderLayoutLblName)).setText(objectMenu.getString("Name"));
                                ((TextView)viewMenuList.findViewById(R.id.takeOrderLayoutLblPrice)).setText(priceHelper.convertToRupiah(price));
                                ((ConstraintLayout)viewMenuList.findViewById(R.id.takeOrderLayoutConstraintLayout)).animate().setStartDelay(120 * i).setDuration(600).alpha(1).translationX(0);

                                Button btnAdd = viewMenuList.findViewById(R.id.takeOrderLayoutBtnAdd);
                                Button btnMinus = viewMenuList.findViewById(R.id.takeOrderLayoutBtnMinus);
                                Button btnPlus = viewMenuList.findViewById(R.id.takeOrderLayoutBtnPlus);
                                EditText txtQty = viewMenuList.findViewById(R.id.takeOrderLayoutTxtQty);

                                txtQty.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        int qty = 0;
                                        if(!txtQty.getText().toString().trim().equals("")) {
                                            qty = Integer.parseInt(txtQty.getText().toString());
                                        }

                                        try {
                                            jsonArrayOrderedMenu = orderedMenuController.editMenuInOrder(jsonArrayOrderedMenu, new JSONObject()
                                                    .put("MenuID", menuID)
                                                    .put("Qty", qty)
                                                    .put("Price", price));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        LoadTotalPrice();
                                    }
                                });

                                btnAdd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        btnAdd.setVisibility(View.GONE);
                                        btnMinus.setVisibility(View.VISIBLE);
                                        btnPlus.setVisibility(View.VISIBLE);
                                        txtQty.setVisibility(View.VISIBLE);

                                        btnPlus.performClick();
                                    }
                                });

                                btnMinus.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(txtQty.getText().toString().trim().equals("")) {
                                            btnAdd.setVisibility(View.VISIBLE);
                                            btnMinus.setVisibility(View.GONE);
                                            btnPlus.setVisibility(View.GONE);
                                            txtQty.setVisibility(View.GONE);
                                        } else {
                                            int qty = Integer.parseInt(txtQty.getText().toString());

                                            if(qty - 1 <= 0) {
                                                btnAdd.setVisibility(View.VISIBLE);
                                                btnMinus.setVisibility(View.GONE);
                                                btnPlus.setVisibility(View.GONE);
                                                txtQty.setVisibility(View.GONE);

                                                qty = 1;
                                            }

                                            qty--;

                                            try {
                                                jsonArrayOrderedMenu = orderedMenuController.editMenuInOrder(jsonArrayOrderedMenu, new JSONObject()
                                                        .put("MenuID", menuID)
                                                        .put("Qty", qty)
                                                        .put("Price", price));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            txtQty.setText(String.valueOf(qty));
                                            LoadTotalPrice();
                                        }
                                    }
                                });

                                btnPlus.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int qty = 0;

                                        if(!txtQty.getText().toString().trim().equals("")) {
                                            qty = Integer.parseInt(txtQty.getText().toString());
                                        }

                                        qty++;

                                        try {
                                            jsonArrayOrderedMenu = orderedMenuController.editMenuInOrder(jsonArrayOrderedMenu, new JSONObject()
                                                    .put("MenuID", menuID)
                                                    .put("Qty", qty)
                                                    .put("Price", price));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        txtQty.setText(String.valueOf(qty));
                                        LoadTotalPrice();
                                    }
                                });

                                linearLayoutOrderMenuList.addView(viewMenuList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }, 1000);
    }

    private void LoadTotalPrice() {
        int totalPrice = 0;

        for(int i = 0; i < jsonArrayOrderedMenu.length(); i++) {
            try {
                JSONObject objectOrderedMenu = jsonArrayOrderedMenu.getJSONObject(i);
                totalPrice += objectOrderedMenu.getInt("Price") * objectOrderedMenu.getInt("Qty");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        lblTotalPrice.setText(priceHelper.convertToRupiah(totalPrice));
    }
}
