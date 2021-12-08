package com.example.gzkitchen;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChefMainHomeFragment extends Fragment {
    View viewInflate;
    ChefMainActivity chefMainActivity;

    public ChefMainHomeFragment(ChefMainActivity chefMainActivityParam) {
        this.chefMainActivity = chefMainActivityParam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.chef_main_home_layout, container, false);

        return viewInflate;
    }
}