package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class MenuItemDetailActivity extends AppCompatActivity {

    ImageView imgBackgroundTop;
    ImageView imgMenu;
    TextView lblName;
    TextView lblIngredients;
    TextView lblDescTitle;
    TextView lblDesc;
    TextView btnBack;
    CardView cardViewBottom;
    TextView lblPrice;
    Button btnOrder;
    RecyclerView recViewIngredients;
    LinearLayout linearLayoutFoodDetails;
    TextView lblFoodDetailTitle;
    TextView lblFoodDetailDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_detail);

        imgBackgroundTop = findViewById(R.id.menuItemDetailImgBackground);
        imgMenu = findViewById(R.id.menuItemDetailImg);
        lblName = findViewById(R.id.menuItemDetailLblName);
        lblIngredients = findViewById(R.id.menuItemDetailLblIngredients);
        lblDescTitle = findViewById(R.id.menuItemDetailLblDescription);
        lblDesc = findViewById(R.id.menuItemDetailLblDescriptionValue);
        btnBack = findViewById(R.id.menuItemDetailBtnBack);
        cardViewBottom = findViewById(R.id.menuItemDetailCardViewBottom);
        lblPrice = findViewById(R.id.menuItemDetailLblPrice);
        btnOrder = findViewById(R.id.menuItemDetailBtnOrder);
        recViewIngredients = findViewById(R.id.menuItemDetailRecViewIngredients);
        linearLayoutFoodDetails = findViewById(R.id.menuItemDetailLinearLayoutFoodDetail);
        lblFoodDetailTitle = findViewById(R.id.menuItemDetailLblDetail);
        lblFoodDetailDesc = findViewById(R.id.menuItemDetailLblDetailDesc);

        LoadAnimation();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuItemDetailActivity.super.onBackPressed();
            }
        });

        try {
            String menuID = getIntent().getStringExtra("MenuID");
            JSONObject object = new MenuController(MenuItemDetailActivity.this).getMenusWhere("ID", menuID).getJSONObject(0);

            String base64Image = object.getString("Image");
            imgMenu.setImageBitmap(new BitmapHelper().convertToBitmap(base64Image));

            lblName.setText(object.getString("Name"));
            lblDesc.setText(object.getString("Description"));
            lblFoodDetailDesc.setText("1 portion of this " + object.getString("Name") + " contains :");

            int price = object.getInt("Price");
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(0);

            lblPrice.setText(formatter.format(price).replace("$", "Rp"));

            JSONArray jsonArrayIngredients = object.getJSONArray("Ingredients");
            recViewIngredients.setAdapter(new IngredientsAdapter(MenuItemDetailActivity.this, jsonArrayIngredients));
            recViewIngredients.setLayoutManager(new LinearLayoutManager(MenuItemDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));


            JSONArray jsonArrayFoodDetails = object.getJSONArray("Details");
            for(int i = 0; i < jsonArrayFoodDetails.length(); i++) {
                View viewFoodDetail = LayoutInflater.from(MenuItemDetailActivity.this).inflate(R.layout.menu_details_layout, null, false);
                ((TextView)viewFoodDetail.findViewById(R.id.menuDetailsLayoutLbl)).setText(jsonArrayFoodDetails.getString(i));

                linearLayoutFoodDetails.addView(viewFoodDetail);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void LoadAnimation() {
        imgBackgroundTop.animate().setDuration(800).alpha(1).translationY(0).setInterpolator(new DecelerateInterpolator());

        btnBack.animate().setDuration(600).setStartDelay(120).alpha(1).translationX(0);
        imgMenu.animate().setDuration(600).setStartDelay(240).alpha(1).translationY(0);
        lblName.animate().setDuration(600).setStartDelay(360).alpha(1).translationY(0);

        lblIngredients.animate().setDuration(600).setStartDelay(480).alpha(1).translationX(0);
        lblDescTitle.animate().setDuration(600).setStartDelay(600).alpha(1).translationX(0);
        lblDesc.animate().setDuration(600).setStartDelay(720).alpha(1).translationY(0);
        lblFoodDetailTitle.animate().setDuration(600).setStartDelay(840).alpha(1).translationX(0);
        lblFoodDetailDesc.animate().setDuration(600).setStartDelay(960).alpha(1).translationY(0);

        cardViewBottom.animate().setDuration(600).setStartDelay(600).alpha(1).translationY(0);
        lblPrice.animate().setDuration(600).setStartDelay(720).alpha(1).translationX(0);
        btnOrder.animate().setDuration(600).setStartDelay(840).alpha(1).translationX(0);
    }
}