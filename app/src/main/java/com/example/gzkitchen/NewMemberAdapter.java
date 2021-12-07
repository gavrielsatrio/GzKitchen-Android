package com.example.gzkitchen;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

class NewMemberAdapter extends RecyclerView.Adapter<NewMemberAdapter.ViewHolder> {
    AdminMainActivity adminMainActivity;
    AdminMainHomeFragment adminMainHomeFragment;
    JSONArray jsonArray;
    View viewInflate;

    public NewMemberAdapter(AdminMainActivity adminMainActivityParam, JSONArray jsonArrayParam, AdminMainHomeFragment adminMainHomeFragmentParam) {
        this.adminMainActivity = adminMainActivityParam;
        this.jsonArray = jsonArrayParam;
        this.adminMainHomeFragment = adminMainHomeFragmentParam;
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
            if(!object.getString("Image").equals("")) {
                ((ImageView)viewInflate.findViewById(R.id.newMemberLayoutImg)).setImageBitmap(new BitmapHelper().convertToBitmap(object.getString("Image")));
            }

            String memberName = object.getString("Name");
            String userID = object.getString("ID");
            ((TextView)viewInflate.findViewById(R.id.newMemberLayoutLblName)).setText(memberName);
            ((TextView)viewInflate.findViewById(R.id.newMemberLayoutLblRole)).setText(object.getString("Role"));

            JSONArray jsonArrayRole = new JSONArray();
            jsonArrayRole.put(new JSONObject().put("Name", "Chef"));
            jsonArrayRole.put(new JSONObject().put("Name", "Cashier"));

            ConstraintLayout constraintLayout = (ConstraintLayout)viewInflate.findViewById(R.id.newMemberConstraintLayout);
            constraintLayout.animate().setDuration(600).alpha(1).translationY(0);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog dialog = new AlertDialog.Builder(adminMainActivity).create();
                    View viewDialog = LayoutInflater.from(adminMainActivity).inflate(R.layout.assign_member_popup_layout, null, false);

                    dialog.setView(viewDialog);
                    dialog.setCancelable(true);

                    dialog.create();

                    Button btnPositive = (Button)viewDialog.findViewById(R.id.assignMemberPopupLayoutBtnGo);
                    Button btnNegative = (Button)viewDialog.findViewById(R.id.assignMemberPopupLayoutBtnCancel);

                    Spinner comboRole = (Spinner)viewDialog.findViewById(R.id.assignMemberPopupLayoutComboRole);

                    comboRole.setAdapter(new ComboBoxAdapter(adminMainActivity, jsonArrayRole));
                    ((TextView)viewDialog.findViewById(R.id.assignMemberPopupLayoutLblHeader)).setText("Assign " + memberName + " to :");

                    btnPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                String selectedRole = jsonArrayRole.getJSONObject(comboRole.getSelectedItemPosition()).getString("Name");
                                new UserController(adminMainActivity).updateRole(userID, selectedRole);

                                dialog.dismiss();

                                constraintLayout.animate().setDuration(600).translationY(-140).alpha(0).setInterpolator(new DecelerateInterpolator());

                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        adminMainActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adminMainHomeFragment.LoadDataNewMembers();
                                            }
                                        });
                                    }
                                }, 1000);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    btnNegative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
