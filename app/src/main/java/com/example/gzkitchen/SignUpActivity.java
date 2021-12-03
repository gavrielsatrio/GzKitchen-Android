package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.regex.Matcher;

public class SignUpActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtConfirmPassword;
    Button btnSignUp;
    TextView btnLogin;

    ImageView imgSignUp;
    TextView lblHeader;
    TextView lblDesc;
    TextView lblAlreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtName = findViewById(R.id.signUpTxtName);
        txtEmail = findViewById(R.id.signUpTxtEmail);
        txtPassword = findViewById(R.id.signUpTxtPassword);
        txtConfirmPassword = findViewById(R.id.signUpTxtConfirmPassword);
        btnSignUp = findViewById(R.id.signUpBtnSignUp);
        btnLogin = findViewById(R.id.signUpBtnLogin);
        imgSignUp = findViewById(R.id.signUpImg);
        lblHeader = findViewById(R.id.signUpLblHeader);
        lblDesc = findViewById(R.id.signUpLblDesc);
        lblAlreadyHaveAccount = findViewById(R.id.signUpLblAlreadyHaveAccount);

        LoadAnimation();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtName.getText().toString().trim().equals("") && !txtEmail.getText().toString().trim().equals("") && !txtPassword.getText().toString().trim().equals("") && !txtConfirmPassword.getText().toString().trim().equals("")) {
                    if(Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString().trim()).matches()) {
                        if(txtPassword.getText().toString().trim().length() >= 8) {
                            if(txtConfirmPassword.getText().toString().trim().equals(txtPassword.getText().toString().trim())) {
                                SharedPreferences sharedPref = getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
                                try {
                                    JSONArray jsonArrayUsers = new JSONArray(sharedPref.getString("", "defaultValue"));
                                    boolean isEmailAlreadyExists = false;
                                    for(int i = 0; i < jsonArrayUsers.length(); i++) {
                                        JSONObject objectUser = jsonArrayUsers.getJSONObject(i);
                                        if(txtEmail.getText().toString().trim().equals(objectUser.getString("Email"))) {
                                            isEmailAlreadyExists = true;
                                            break;
                                        }
                                    }

                                    if(isEmailAlreadyExists == false) {
                                        jsonArrayUsers.put(new JSONObject()
                                                .put("Name", txtName.getText().toString().trim())
                                                .put("Email", txtEmail.getText().toString().trim())
                                                .put("Password", txtPassword.getText().toString().trim())
                                                .put("Role", "Member")
                                        );
                                        sharedPref.edit().putString("Users", jsonArrayUsers.toString()).apply();

                                        Intent intent = new Intent(SignUpActivity.this, MemberMainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                        finish();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "This email is already registered", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(SignUpActivity.this, "Confirm password doesn't match with the password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Password must contains at least 8 characters", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Email format is not valid", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Please fill up all information", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void LoadAnimation() {
        imgSignUp.animate().setDuration(600).alpha(1);
        lblHeader.animate().setDuration(600).setStartDelay(120).alpha(1).translationY(0);
        lblDesc.animate().setDuration(600).setStartDelay(240).alpha(1).translationY(0);
        lblAlreadyHaveAccount.animate().setDuration(600).setStartDelay(360).alpha(1).translationY(0);
        btnLogin.animate().setDuration(600).setStartDelay(480).alpha(1).translationY(0);

        txtName.animate().setDuration(600).setStartDelay(600).alpha(1).translationX(0);
        txtEmail.animate().setDuration(600).setStartDelay(720).alpha(1).translationX(0);
        txtPassword.animate().setDuration(600).setStartDelay(840).alpha(1).translationX(0);
        txtConfirmPassword.animate().setDuration(600).setStartDelay(960).alpha(1).translationX(0);

        btnSignUp.animate().setDuration(600).setStartDelay(1080).alpha(1).translationY(0);
    }
}