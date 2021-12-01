package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    TextView btnSignUp;
    Button btnLogin;
    ImageView imgLogin;

    TextView lblHeader;
    TextView lblDesc;
    TextView lblDoesntHaveAccount;

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
                Intent intent = new Intent(LoginActivity.this, MemberMainActivity.class);
                startActivity(intent);
                finish();
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