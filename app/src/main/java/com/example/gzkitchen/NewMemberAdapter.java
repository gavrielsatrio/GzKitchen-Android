package com.example.gzkitchen;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NewMemberAdapter extends RecyclerView.Adapter<NewMemberAdapter.ViewHolder> {
    AdminMainActivity adminMainActivity;
    JSONArray jsonArray;
    View viewInflate;

    public NewMemberAdapter(AdminMainActivity adminMainActivityParam, JSONArray jsonArrayParam) {
        this.adminMainActivity = adminMainActivityParam;
        this.jsonArray = jsonArrayParam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewInflate = LayoutInflater.from(adminMainActivity).inflate(R.layout.new_member_layout, parent, false);

        return new ViewHolder(viewInflate);
    }

    @Override
    public void onBindViewHolder(NewMemberAdapter.ViewHolder holder, int position) {
        try {
            JSONObject object = jsonArray.getJSONObject(position);
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
