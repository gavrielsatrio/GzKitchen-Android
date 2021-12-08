package com.example.gzkitchen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChefMainTakeOrderFragment extends Fragment {
    ChefMainActivity chefMainActivity;
    View viewInflate;

    public ChefMainTakeOrderFragment(ChefMainActivity chefMainActivityParam) {
        this.chefMainActivity = chefMainActivityParam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.chef_main_take_order_layout, container, false);

        return viewInflate;
    }
}
