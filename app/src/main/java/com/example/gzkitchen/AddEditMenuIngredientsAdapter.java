package com.example.gzkitchen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class AddEditMenuIngredientsAdapter extends RecyclerView.Adapter<AddEditMenuIngredientsAdapter.ViewHolder> {
    AddEditMenuActivity addEditMenuActivity;
    JSONArray jsonArray;
    View viewInflate;

    public AddEditMenuIngredientsAdapter(AddEditMenuActivity addEditMenuActivityParam, JSONArray jsonArrayParam) {
        this.addEditMenuActivity = addEditMenuActivityParam;
        this.jsonArray = jsonArrayParam;
    }

    @Override
    public AddEditMenuIngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewInflate = LayoutInflater.from(addEditMenuActivity).inflate(R.layout.add_edit_menu_ingredients_layout, parent, false);

        return new ViewHolder(viewInflate);
    }

    @Override
    public void onBindViewHolder(AddEditMenuIngredientsAdapter.ViewHolder holder, int position) {
        try {
            JSONObject object = jsonArray.getJSONObject(position);

            ((TextView)viewInflate.findViewById(R.id.addEditMenuIngredientsLayoutLblName)).setText(object.getString("Name"));
            ((CardView)viewInflate.findViewById(R.id.addEditMenuIngredientsLayoutCardView)).animate().setDuration(600).setStartDelay(200).alpha(1).translationY(0);
            FloatingActionButton btnDelete = (FloatingActionButton)viewInflate.findViewById(R.id.addEditMenuIngredientsBtnDelete);
            btnDelete.animate().setDuration(600).setStartDelay(200).alpha(1).translationY(0);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addEditMenuActivity.jsonArrayMenuIngredients.remove(position);
                    addEditMenuActivity.LoadDataIngredients();
                }
            });
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
