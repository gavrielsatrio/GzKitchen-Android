package com.example.gzkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gzkitchen.Controllers.UserController;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChefMainHomeFragment extends Fragment {
    View viewInflate;
    ChefMainActivity chefMainActivity;
    UserController userController;

    TextView lblName;
    TextView lblRole;
    ImageView btnProfile;
    LinearLayout linearLayoutCurrentlyCooking;

    public ChefMainHomeFragment(ChefMainActivity chefMainActivityParam) {
        this.chefMainActivity = chefMainActivityParam;
        this.userController = new UserController(chefMainActivity);
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

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chefMainActivity, ProfileActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        return viewInflate;
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
