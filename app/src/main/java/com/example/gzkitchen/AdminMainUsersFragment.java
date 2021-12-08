package com.example.gzkitchen;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AdminMainUsersFragment extends Fragment {
    View viewInflate;
    AdminMainActivity adminMainActivity;

    TextView lblHeader;
    TextView lblDesc;
    EditText txtSearch;
    ImageView btnSearch;
    LinearLayout linearLayoutUsers;

    public AdminMainUsersFragment(AdminMainActivity adminMainActivityParam) {
        this.adminMainActivity = adminMainActivityParam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.admin_main_users_fragment_layout, container, false);

        lblHeader = viewInflate.findViewById(R.id.adminMainUsersLblHeader);
        lblDesc = viewInflate.findViewById(R.id.adminMainUsersLblDesc);
        txtSearch = viewInflate.findViewById(R.id.adminMainUsersTxtSearch);
        btnSearch = viewInflate.findViewById(R.id.adminMainUsersBtnSearch);
        linearLayoutUsers = viewInflate.findViewById(R.id.adminMainUsersLinearLayoutUsers);

        LoadData();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData();
            }
        });

        return viewInflate;
    }

    public void LoadData() {
        UserController userController = new UserController(adminMainActivity);
        linearLayoutUsers.removeAllViews();

        JSONArray jsonArrayUsers = new JSONArray();
        if(!txtSearch.getText().toString().trim().equals("")) {
            jsonArrayUsers = userController.getSearchUser(txtSearch.getText().toString().trim());
        } else {
            jsonArrayUsers = userController.getAllUsers();
        }

        for(int i = 0; i < jsonArrayUsers.length(); i++) {
            try {
                JSONObject objectUser = jsonArrayUsers.getJSONObject(i);
                View viewUsers = LayoutInflater.from(adminMainActivity).inflate(R.layout.user_item_large_layout, null, false);

                if(!objectUser.getString("Image").equals("")) {
                    ((ImageView)viewUsers.findViewById(R.id.userItemLargeLayoutImg)).setImageBitmap(new BitmapHelper().convertToBitmap(objectUser.getString("Image")));
                }

                String userID = objectUser.getString("ID");
                String userName = objectUser.getString("Name");
                ((TextView)viewUsers.findViewById(R.id.userItemLargeLayoutLblName)).setText(userName);
                ((TextView)viewUsers.findViewById(R.id.userItemLargeLayoutLblEmail)).setText(objectUser.getString("Email"));
                ((TextView)viewUsers.findViewById(R.id.userItemLargeLayoutLblRole)).setText(objectUser.getString("Role"));

                JSONArray jsonArrayRole = new JSONArray();
                jsonArrayRole.put(new JSONObject().put("Name", "Chef"));
                jsonArrayRole.put(new JSONObject().put("Name", "Cashier"));

                CardView cardView = (CardView)viewUsers.findViewById(R.id.userItemLargeLayoutCardView);
                cardView.animate().setDuration(600).setStartDelay(120 * i).alpha(1).translationY(0);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(adminMainActivity);
                        View viewDialog = LayoutInflater.from(adminMainActivity).inflate(R.layout.assign_member_popup_layout, null, false);

                        AlertDialog dialog = builder.create();
                        dialog.setView(viewDialog);
                        dialog.setCancelable(true);

                        Button btnPositive = (Button)viewDialog.findViewById(R.id.assignMemberPopupLayoutBtnGo);
                        Button btnNegative = (Button)viewDialog.findViewById(R.id.assignMemberPopupLayoutBtnCancel);
                        Spinner comboRole = (Spinner)viewDialog.findViewById(R.id.assignMemberPopupLayoutComboRole);

                        comboRole.setAdapter(new ComboBoxAdapter(adminMainActivity, jsonArrayRole));
                        ((TextView)viewDialog.findViewById(R.id.assignMemberPopupLayoutLblHeader)).setText("Assign " + userName + " to :");

                        btnPositive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    String selectedRole = jsonArrayRole.getJSONObject(comboRole.getSelectedItemPosition()).getString("Name");
                                    new UserController(adminMainActivity).updateRole(userID, selectedRole);

                                    LoadData();
                                    dialog.dismiss();
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

                linearLayoutUsers.addView(viewUsers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
