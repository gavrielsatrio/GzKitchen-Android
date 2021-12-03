package com.example.gzkitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import androidx.annotation.NonNull;
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
