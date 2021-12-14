package com.example.gzkitchen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class CashierMainTakeOrderFragment extends Fragment {
    CashierMainActivity cashierMainActivity;
    View viewInflate;

    public CashierMainTakeOrderFragment(CashierMainActivity cashierMainActivityParam) {
        this.cashierMainActivity = cashierMainActivityParam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.cashier_main_take_order_layout, container, false);

        return viewInflate;
    }
}
