package com.example.gzkitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    Context context;
    JSONArray jsonArray;
    View viewInflate;

    public IngredientsAdapter(Context contextParam, JSONArray jsonArrayParam) {
        this.context = contextParam;
        this.jsonArray = jsonArrayParam;
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewInflate = LayoutInflater.from(context).inflate(R.layout.ingredients_layout, parent, false);

        return new ViewHolder(viewInflate);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder holder, int position) {
        try {
            JSONObject object = jsonArray.getJSONObject(position);

            ((TextView)viewInflate.findViewById(R.id.ingredientsLayoutLblName)).setText(object.getString("Name"));
            ((CardView)viewInflate.findViewById(R.id.ingredientsLayoutCardView)).animate().setDuration(600).setStartDelay(200).alpha(1).translationY(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
