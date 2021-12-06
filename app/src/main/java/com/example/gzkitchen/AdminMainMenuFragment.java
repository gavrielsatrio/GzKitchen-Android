package com.example.gzkitchen;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class AdminMainMenuFragment extends Fragment {
    AdminMainActivity adminMainActivity;
    View viewInflate;
    TextView lblHeader;
    TextView lblDesc;
    EditText txtSearch;
    ImageView btnSearch;
    LinearLayout linearLayoutMenu;

    public AdminMainMenuFragment(AdminMainActivity adminMainActivityParam) {
        this.adminMainActivity = adminMainActivityParam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.admin_main_menu_fragment_layout, container, false);

        lblHeader = viewInflate.findViewById(R.id.adminMainMenuLblHeader);
        lblDesc = viewInflate.findViewById(R.id.adminMainMenuLblDesc);
        txtSearch = viewInflate.findViewById(R.id.adminMainMenuTxtSearch);
        btnSearch = viewInflate.findViewById(R.id.adminMainMenuBtnSearch);
        linearLayoutMenu = viewInflate.findViewById(R.id.adminMainMenuLinearLayout);

        LoadAnimation();
        LoadData();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData();
            }
        });

        return viewInflate;
    }

    private void LoadAnimation() {

    }

    private void LoadData() {
        linearLayoutMenu.removeAllViews();
        MenuController menuController = new MenuController(adminMainActivity);

        JSONArray jsonArrayMenu = new JSONArray();
        if(!txtSearch.getText().toString().trim().equals("")) {
            jsonArrayMenu = menuController.getMenusWhere("Name", txtSearch.getText().toString().trim());
        } else {
            jsonArrayMenu = menuController.getMenus();
        }

        for(int i = 0; i < jsonArrayMenu.length(); i++) {
            try {
                JSONObject objectMenu = jsonArrayMenu.getJSONObject(i);
                View viewMenu = LayoutInflater.from(adminMainActivity).inflate(R.layout.menu_item_large_layout, null, false);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ImageView)viewMenu.findViewById(R.id.menuItemLargeLargeLayoutImg)).setImageDrawable(adminMainActivity.getDrawable(objectMenu.getInt("Image")));
                } else {
                    ((ImageView)viewMenu.findViewById(R.id.menuItemLargeLargeLayoutImg)).setImageDrawable(adminMainActivity.getResources().getDrawable(objectMenu.getInt("Image")));
                }

                ((TextView)viewMenu.findViewById(R.id.menuItemLargeLayoutLblName)).setText(objectMenu.getString("Name"));

                int price = objectMenu.getInt("Price");
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                formatter.setMaximumFractionDigits(0);

                ((TextView)viewMenu.findViewById(R.id.menuItemLargeLayoutLblPrice)).setText(formatter.format(price).replace("$", "Rp"));
                ((TextView)viewMenu.findViewById(R.id.menuItemLargeLayoutLblIngredientsCount)).setText("Ingredients : " + objectMenu.getJSONArray("Ingredients").length());

                ((ConstraintLayout)viewMenu.findViewById(R.id.menuItemLargeLayoutConstraintLayout)).animate().setDuration(600).alpha(1).translationY(0);

                linearLayoutMenu.addView(viewMenu);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
