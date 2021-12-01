package com.example.gzkitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class EmployeeOfTheMonthAdapter extends RecyclerView.Adapter<EmployeeOfTheMonthAdapter.ViewHolder> {
    View viewInflate;
    Context context;
    JSONArray jsonArray;

    public EmployeeOfTheMonthAdapter(Context contextParam, JSONArray jsonArrayParam) {
        this.context = contextParam;
        this.jsonArray = jsonArrayParam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewInflate = LayoutInflater.from(context).inflate(R.layout.employee_of_the_month_layout, parent, false);

        return new ViewHolder(viewInflate);
    }

    @Override
    public void onBindViewHolder(EmployeeOfTheMonthAdapter.ViewHolder holder, int position) {
        try {
            JSONObject object = jsonArray.getJSONObject(position);
            ((TextView)viewInflate.findViewById(R.id.employeeOfTheMonthLayoutLblName)).setText(object.getString("Name"));
            ((TextView)viewInflate.findViewById(R.id.employeeOfTheMonthLayoutLblMonth)).setText(object.getString("Month"));
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
