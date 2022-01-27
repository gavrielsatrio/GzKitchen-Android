package com.example.gzkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gzkitchen.Controllers.MenuController;
import com.example.gzkitchen.Helper.BitmapHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AdminMainMenuFragment extends Fragment {
    AdminMainActivity adminMainActivity;
    View viewInflate;
    TextView lblHeader;
    TextView lblDesc;
    EditText txtSearch;
    ImageView btnSearch;
    LinearLayout linearLayoutMenu;
    FloatingActionButton btnAdd;

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
        btnAdd = viewInflate.findViewById(R.id.adminMainMenuBtnAdd);

        LoadAnimation();
        LoadData();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminMainActivity, AddEditMenuActivity.class);
                startActivity(intent);
            }
        });

        return viewInflate;
    }

    private void LoadAnimation() {
        lblHeader.animate().setDuration(600).alpha(1).translationY(0);
        lblDesc.animate().setDuration(600).setStartDelay(120).alpha(1).translationY(0);
        txtSearch.animate().setDuration(600).setStartDelay(240).alpha(1).translationX(0);
        btnSearch.animate().setDuration(600).setStartDelay(360).alpha(1).translationX(0);
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

                String menuID = objectMenu.getString("ID");
                String base64Image = objectMenu.getString("Image");
                ((ImageView)viewMenu.findViewById(R.id.menuItemLargeLargeLayoutImg)).setImageBitmap(new BitmapHelper().convertToBitmap(base64Image));
                ((TextView)viewMenu.findViewById(R.id.menuItemLargeLayoutLblName)).setText(objectMenu.getString("Name"));

                int price = objectMenu.getInt("Price");
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                formatter.setMaximumFractionDigits(0);

                ((TextView)viewMenu.findViewById(R.id.menuItemLargeLayoutLblPrice)).setText(formatter.format(price).replace("$", "Rp"));
                ((TextView)viewMenu.findViewById(R.id.menuItemLargeLayoutLblIngredientsCount)).setText("Ingredients : " + objectMenu.getJSONArray("Ingredients").length());

                CardView cardView = (CardView) viewMenu.findViewById(R.id.menuItemLargeLayoutCardView);
                cardView.animate().setDuration(600).setStartDelay(120 * i).alpha(1).translationY(0);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(adminMainActivity, AddEditMenuActivity.class);
                        intent.putExtra("MenuID", menuID);
                        startActivity(intent);
                    }
                });

                linearLayoutMenu.addView(viewMenu);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
