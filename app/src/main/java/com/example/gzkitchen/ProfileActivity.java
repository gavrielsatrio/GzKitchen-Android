package com.example.gzkitchen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    ImageView imgBackgroundTop;
    CardView cardViewBackground;
    ImageView imgProfile;
    Button btnLogout;
    TextView lblName;
    TextView lblRole;
    TextView lblEmail;
    TextView btnBack;
    Button btnEditProfile;
    ImageView imgBtnEditProfile;
    Button btnAboutDeveloper;
    ImageView imgBtnAboutDeveloper;

    JSONObject objectUser = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgBackgroundTop = findViewById(R.id.profileImgTop);
        cardViewBackground = findViewById(R.id.profileCardView);
        imgProfile = findViewById(R.id.profileImgProfile);
        btnLogout = findViewById(R.id.profileBtnLogout);
        lblName = findViewById(R.id.profileLblName);
        lblRole = findViewById(R.id.profileLblRole);
        lblEmail = findViewById(R.id.profileLblEmail);
        btnBack = findViewById(R.id.profileBtnBack);
        btnEditProfile = findViewById(R.id.profileBtnEditProfile);
        imgBtnEditProfile = findViewById(R.id.profileImgEditProfile);
        btnAboutDeveloper = findViewById(R.id.profileBtnAboutDeveloper);
        imgBtnAboutDeveloper = findViewById(R.id.profileImgAboutDeveloper);

        LoadData();
        LoadAnimation();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.super.onBackPressed();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View viewDialog = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.edit_profile_popup_layout, null, false);

                builder.setView(viewDialog);
                builder.setCancelable(true);

                AlertDialog dialog = builder.create();

                ((TextView)viewDialog.findViewById(R.id.editProfilePopupBtnCancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                EditText txtName = (EditText)viewDialog.findViewById(R.id.editProfilePopupTxtName);
                EditText txtEmail = (EditText)viewDialog.findViewById(R.id.editProfilePopupTxtEmail);
                EditText txtPassword = (EditText)viewDialog.findViewById(R.id.editProfilePopupTxtPassword);

                try {
                    txtName.setText(objectUser.getString("Name"));
                    txtEmail.setText(objectUser.getString("Email"));
                    txtPassword.setText(objectUser.getString("Password"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ((Button)viewDialog.findViewById(R.id.editProfilePopupBtnSave)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!txtName.getText().toString().trim().equals("") && !txtEmail.getText().toString().trim().equals("") && !txtPassword.getText().toString().trim().equals("")) {
                            if(Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString().trim()).matches()) {
                                if(txtPassword.getText().toString().trim().length() >= 8) {
                                    new UserController().updateUserProfile(ProfileActivity.this, txtName.getText().toString().trim(), txtEmail.getText().toString().trim(), txtPassword.getText().toString().trim());

                                    LoadData();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Password must contains at least 8 characters", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(ProfileActivity.this, "Email format is not valid", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Please fill up the profile information", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                dialog.show();
            }
        });

        btnAboutDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserController().logoutUser(ProfileActivity.this);

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                finish();
            }
        });
    }

    private void LoadAnimation() {
        imgBackgroundTop.animate().setDuration(800).alpha(1).translationY(0).setInterpolator(new DecelerateInterpolator());

        btnBack.animate().setDuration(600).setStartDelay(120).alpha(1).translationX(0);
        cardViewBackground.animate().setDuration(600).setStartDelay(240).alpha(1).translationY(0);
        imgProfile.animate().setDuration(600).setStartDelay(360).alpha(1).translationY(0);

        lblName.animate().setDuration(600).setStartDelay(480).alpha(1).translationY(0);
        lblRole.animate().setDuration(600).setStartDelay(600).alpha(1).translationX(0);
        lblEmail.animate().setDuration(600).setStartDelay(720).alpha(1).translationX(0);

        btnEditProfile.animate().setDuration(600).setStartDelay(840).alpha(1).translationY(0);
        imgBtnEditProfile.animate().setDuration(600).setStartDelay(840).alpha(1).translationY(0);

        btnAboutDeveloper.animate().setDuration(600).setStartDelay(960).alpha(1).translationY(0);
        imgBtnAboutDeveloper.animate().setDuration(600).setStartDelay(960).alpha(1).translationY(0);

        btnLogout.animate().setDuration(600).setStartDelay(1080).alpha(1).translationY(0);
    }

    private void LoadData() {
        objectUser = new UserController().getLoggedInUserObject(ProfileActivity.this);
        try {
            lblName.setText(objectUser.getString("Name"));
            lblRole.setText(objectUser.getString("Role"));
            lblEmail.setText(objectUser.getString("Email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}