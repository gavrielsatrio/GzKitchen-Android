package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.Inet4Address;

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
                Intent intent = new Intent(SignUpActivity.this, MemberMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
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