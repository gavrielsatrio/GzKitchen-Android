package com.example.gzkitchen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminMainUsersFragment extends Fragment {
    View viewInflate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.admin_main_users_layout, container, false);

        return viewInflate;
    }
}
