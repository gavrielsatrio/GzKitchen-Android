package com.example.gzkitchen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzkitchen.Helper.BitmapHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

class MenuItemSmallAdapter extends RecyclerView.Adapter<MenuItemSmallAdapter.ViewHolder> {
    View viewInflate;
    Context context;
    JSONArray jsonArray;

    public MenuItemSmallAdapter(Context contextParam, JSONArray jsonArrayParam) {
        this.context = contextParam;
        this.jsonArray = jsonArrayParam;
    }

    @Override
    public MenuItemSmallAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewInflate = LayoutInflater.from(context).inflate(R.layout.menu_item_small_layout, parent, false);

        return new ViewHolder(viewInflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject object = jsonArray.getJSONObject(position);

            String menuID = object.getString("ID");
            String base64Image = object.getString("Image");

            ((ImageView)viewInflate.findViewById(R.id.menuItemSmallLayoutImg)).setImageBitmap(new BitmapHelper().convertToBitmap(base64Image));
            ((ImageView)viewInflate.findViewById(R.id.menuItemSmallLayoutImg)).setBackground(null);
            ((TextView)viewInflate.findViewById(R.id.menuItemSmallLayoutLblName)).setText(object.getString("Name"));

            ConstraintLayout layout = (ConstraintLayout)viewInflate.findViewById(R.id.menuItemSmallLayoutConstraintLayout);
            layout.animate().setDuration(800).alpha(1).translationY(0);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MenuItemDetailActivity.class);
                    intent.putExtra("MenuID", menuID);
                    context.startActivity(intent);
                }
            });

            int price = object.getInt("Price");
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(0);

            ((TextView)viewInflate.findViewById(R.id.menuItemSmallLayoutLblPrice)).setText(formatter.format(price).replace("$", "Rp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
