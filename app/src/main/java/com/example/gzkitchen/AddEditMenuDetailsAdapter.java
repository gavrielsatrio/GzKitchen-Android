package com.example.gzkitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class AddEditMenuDetailsAdapter extends BaseAdapter {
    AddEditMenuActivity addEditMenuActivity;
    JSONArray jsonArray;

    public AddEditMenuDetailsAdapter(AddEditMenuActivity addEditMenuActivityParam, JSONArray jsonArrayParam) {
        this.addEditMenuActivity = addEditMenuActivityParam;
        this.jsonArray = jsonArrayParam;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return -1;
    }

    @Override
    public long getItemId(int i) {
        return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewInflate = LayoutInflater.from(addEditMenuActivity).inflate(R.layout.add_edit_menu_details_layout, null, false);

        try {
            ((TextView)viewInflate.findViewById(R.id.addEditMenuDetailsLayoutLbl)).setText(jsonArray.getString(i));
            ((TextView)viewInflate.findViewById(R.id.addEditMenuDetailsLayoutBtnDelete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addEditMenuActivity.jsonArrayMenuDetails.remove(i);
                    addEditMenuActivity.LoadDataDetails();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return viewInflate;
    }
}
