package com.example.gzkitchen;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ChefMainHomeFragment extends Fragment {
    View viewInflate;
    ChefMainActivity chefMainActivity;

    ImageView imgTop;
    TextView lblHello;
    TextView lblName;
    TextView lblRole;
    ImageView btnProfile;

    public ChefMainHomeFragment(ChefMainActivity chefMainActivityParam) {
        this.chefMainActivity = chefMainActivityParam;
    }

    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.chef_main_home_layout, container, false);

        imgTop = viewInflate.findViewById(R.id.chefMainHomeImgTop);
        lblHello = viewInflate.findViewById(R.id.chefMainHomeLblHello);
        lblName = viewInflate.findViewById(R.id.chefMainHomeLblName);
        lblRole = viewInflate.findViewById(R.id.chefMainHomeLblRole);
        btnProfile = viewInflate.findViewById(R.id.chefMainHomeBtnProfile);

        LoadDataChef();



        return viewInflate;
    }

    private void LoadDataChef() {
        UserController userController = new UserController(chefMainActivity);
        JSONObject objectUser = userController.getLoggedInUserObject();

        try {
            lblName.setText(objectUser.getString("Name"));
            lblRole.setText(objectUser.getString("Role"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
