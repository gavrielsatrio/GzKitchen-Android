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

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CashierMainTakeOrderFragment extends Fragment {
    CashierMainActivity cashierMainActivity;
    View viewInflate;
    OrderController orderController;

    TextView lblHeader;
    EditText txtSearch;
    ImageView btnSearch;
    LinearLayout linearLayoutOrderMenuList;
    CardView cardViewBottom;
    TextView lblPrice;
    Button btnPay;

    public CashierMainTakeOrderFragment(CashierMainActivity cashierMainActivityParam) {
        this.cashierMainActivity = cashierMainActivityParam;
        orderController = new OrderController(cashierMainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.cashier_main_take_order_layout, container, false);

        lblHeader = viewInflate.findViewById(R.id.cashierMainTakeOrderLblHeader);
        txtSearch = viewInflate.findViewById(R.id.cashierMainTakeOrderTxtSearch);
        btnSearch = viewInflate.findViewById(R.id.cashierMainTakeOrderBtnSearch);
        linearLayoutOrderMenuList = viewInflate.findViewById(R.id.cashierMainTakeOrderLinearLayoutOrderMenuList);
        cardViewBottom = viewInflate.findViewById(R.id.cashierMainTakeOrderCardViewBottom);
        lblPrice = viewInflate.findViewById(R.id.cashierMainTakeOrderLblPrice);
        btnPay = viewInflate.findViewById(R.id.cashierMainTakeOrderBtnProceedPayment);

        JSONArray jsonArrayOrderMenu = orderController.getOrder();
        for(int i = 0; i < jsonArrayOrderMenu.length(); i++) {
            View viewOrderMenu = LayoutInflater.from(cashierMainActivity).inflate(R.layout.take_order_layout, null, false);
            linearLayoutOrderMenuList.addView(viewOrderMenu);
        }

        return viewInflate;
    }
}
