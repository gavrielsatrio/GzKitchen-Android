package com.example.gzkitchen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SplashScreenRecViewAdapter extends RecyclerView.Adapter<SplashScreenRecViewAdapter.ViewHolder> {
    SplashScreenActivity splashScreenActivity;
    JSONArray jsonArray;
    View viewInflate;

    public SplashScreenRecViewAdapter(SplashScreenActivity splashScreenActivityParam, JSONArray jsonArrayParam) {
        this.splashScreenActivity = splashScreenActivityParam;
        this.jsonArray = jsonArrayParam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewInflate = LayoutInflater.from(splashScreenActivity).inflate(R.layout.splash_screen_recview_layout, parent, false);
        return new ViewHolder(viewInflate);
    }

    @Override
    public void onBindViewHolder(SplashScreenRecViewAdapter.ViewHolder holder, int position) {
        try {
            JSONObject object = jsonArray.getJSONObject(position);

            ImageView img = (ImageView)viewInflate.findViewById(R.id.splashScreenRecViewLayoutImg);
            TextView lblTitle = (TextView)viewInflate.findViewById(R.id.splashScreenRecViewLayoutLblTitle);
            TextView lblDesc = (TextView)viewInflate.findViewById(R.id.splashScreenRecViewLayoutLblDesc);

            img.setImageDrawable(splashScreenActivity.getDrawable(object.getInt("Image")));
            lblTitle.setText(object.getString("Title"));
            lblDesc.setText(object.getString("Desc"));

            img.animate().setDuration(500).alpha(1);
            lblTitle.animate().setDuration(500).alpha(1).setStartDelay(200);
            lblDesc.animate().setDuration(500).alpha(1).setStartDelay(400);
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
