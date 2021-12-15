package com.example.gzkitchen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CashierMainTakeOrderFragment extends Fragment {
    CashierMainActivity cashierMainActivity;
    View viewInflate;
    OrderHeaderController orderHeaderController;
    MenuController menuController;
    PriceHelper priceHelper = new PriceHelper();

    TextView lblHeader;
    EditText txtSearch;
    ImageView btnSearch;
    LinearLayout linearLayoutOrderMenuList;
    CardView cardViewBottom;
    TextView lblTotalPrice;
    Button btnPay;

    JSONArray jsonArrayOrderedMenu = new JSONArray();

    public CashierMainTakeOrderFragment(CashierMainActivity cashierMainActivityParam) {
        this.cashierMainActivity = cashierMainActivityParam;
        orderHeaderController = new OrderHeaderController(cashierMainActivity);
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
        btnPay = viewInflate.findViewById(R.id.cashierMainTakeOrderBtnProceedPayment);

        JSONArray jsonArrayOrderMenu = menuController.getMenus();
        for(int i = 0; i < jsonArrayOrderMenu.length(); i++) {
            try {
                JSONObject objectMenu = jsonArrayOrderMenu.getJSONObject(i);
                View viewOrderMenu = LayoutInflater.from(cashierMainActivity).inflate(R.layout.take_order_menu_layout, null, false);

                int menuID = objectMenu.getInt("ID");
                int price = objectMenu.getInt("Price");

                ((ImageView)viewOrderMenu.findViewById(R.id.takeOrderLayoutImg)).setImageBitmap(new BitmapHelper().convertToBitmap(objectMenu.getString("Image")));
                ((TextView)viewOrderMenu.findViewById(R.id.takeOrderLayoutLblName)).setText(objectMenu.getString("Name"));
                ((TextView)viewOrderMenu.findViewById(R.id.takeOrderLayoutLblPrice)).setText(priceHelper.convertToRupiah(price));

                Button btnAdd = viewOrderMenu.findViewById(R.id.takeOrderLayoutBtnAdd);
                Button btnMinus = viewOrderMenu.findViewById(R.id.takeOrderLayoutBtnMinus);
                Button btnPlus = viewOrderMenu.findViewById(R.id.takeOrderLayoutBtnPlus);
                EditText txtQty = viewOrderMenu.findViewById(R.id.takeOrderLayoutTxtQty);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnAdd.setVisibility(View.GONE);
                        btnMinus.setVisibility(View.VISIBLE);
                        btnPlus.setVisibility(View.VISIBLE);
                        txtQty.setVisibility(View.VISIBLE);

                        txtQty.setText("1");
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
                            qty--;

                            txtQty.setText(String.valueOf(qty));

                            if(qty == 0) {
                                btnAdd.setVisibility(View.VISIBLE);
                                btnMinus.setVisibility(View.GONE);
                                btnPlus.setVisibility(View.GONE);
                                txtQty.setVisibility(View.GONE);
                            }
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
                            jsonArrayOrderedMenu.put(new JSONObject()
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

                linearLayoutOrderMenuList.addView(viewOrderMenu);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return viewInflate;
    }

    private void LoadTotalPrice() {
        int totalPrice = 0;

        lblTotalPrice.setText(priceHelper.convertToRupiah(totalPrice));
    }
}
