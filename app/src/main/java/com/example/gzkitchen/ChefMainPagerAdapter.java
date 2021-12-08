package com.example.gzkitchen;

import org.json.JSONArray;
import org.json.JSONException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ChefMainPagerAdapter extends FragmentStateAdapter {
    JSONArray jsonArray;

    public ChefMainPagerAdapter(FragmentActivity fragmentActivity, JSONArray jsonArrayParam) {
        super(fragmentActivity);
        this.jsonArray = jsonArrayParam;
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragmentReturn = null;
        try {
            fragmentReturn = (Fragment) jsonArray.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fragmentReturn;
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}
