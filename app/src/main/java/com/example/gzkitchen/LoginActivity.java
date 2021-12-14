package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    TextView btnSignUp;
    Button btnLogin;
    ImageView imgLogin;

    TextView lblHeader;
    TextView lblDesc;
    TextView lblDoesntHaveAccount;

    JSONArray jsonArrayUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.loginTxtEmail);
        txtPassword = findViewById(R.id.loginTxtPassword);
        btnSignUp = findViewById(R.id.loginBtnSignUp);
        btnLogin = findViewById(R.id.loginBtnLogin);
        imgLogin = findViewById(R.id.loginImg);
        lblHeader = findViewById(R.id.loginLblHeader);
        lblDesc = findViewById(R.id.loginLblDesc);
        lblDoesntHaveAccount = findViewById(R.id.loginLblDoesntHaveAccount);

        LoadAnimation();

        SharedPreferences sharedPref = getSharedPreferences("AppLocalData", Context.MODE_PRIVATE);
        try {
            jsonArrayUsers = new JSONArray(sharedPref.getString("Users", "defaultValue"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtEmail.getText().toString().trim().equals("") && !txtPassword.getText().toString().trim().equals("")) {
                    boolean isLoginValid = false;
                    String userRole = "";
                    for(int i = 0; i < jsonArrayUsers.length(); i++) {
                        try {
                            JSONObject objectUser = jsonArrayUsers.getJSONObject(i);
                            if(txtEmail.getText().toString().trim().equals(objectUser.getString("Email")) && txtPassword.getText().toString().trim().equals(objectUser.getString("Password"))) {
                                isLoginValid = true;
                                userRole = objectUser.getString("Role");
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if(isLoginValid) {
                        sharedPref.edit().putString("LoggedInUserEmail", txtEmail.getText().toString().trim()).apply();

                        if(userRole.equals("Admin")) {
                            Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                            intent.putExtra("Email", txtEmail.getText().toString().trim());
                            startActivity(intent);
                        } else if(userRole.equals("Cashier")) {
                            Intent intent = new Intent(LoginActivity.this, CashierMainActivity.class);
                            intent.putExtra("Email", txtEmail.getText().toString().trim());
                            startActivity(intent);
                        } else if(userRole.equals("Chef")) {
                            Intent intent = new Intent(LoginActivity.this, ChefMainActivity.class);
                            intent.putExtra("Email", txtEmail.getText().toString().trim());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MemberMainActivity.class);
                            intent.putExtra("Email", txtEmail.getText().toString().trim());
                            startActivity(intent);
                        }

                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill up the login information", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void LoadAnimation() {
        imgLogin.animate().setDuration(600).alpha(1);
        lblHeader.animate().setDuration(600).setStartDelay(120).alpha(1).translationY(0);
        lblDesc.animate().setDuration(600).setStartDelay(240).alpha(1).translationY(0);
        lblDoesntHaveAccount.animate().setDuration(600).setStartDelay(360).alpha(1).translationY(0);
        btnSignUp.animate().setDuration(600).setStartDelay(480).alpha(1).translationY(0);

        txtEmail.animate().setDuration(600).setStartDelay(600).alpha(1).translationX(0);
        txtPassword.animate().setDuration(600).setStartDelay(720).alpha(1).translationX(0);

        btnLogin.animate().setDuration(600).setStartDelay(840).alpha(1).translationY(0);
    }
}