package com.example.finlis_20_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText loginUsername;
    EditText loginPassword;
    Button loginButton;
    TextView tvToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        賬戶名和密碼
        loginUsername = (EditText)findViewById(R.id.loginUsername);
        loginPassword = (EditText)findViewById(R.id.loginPassword);

//        登入按鈕
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

//        註冊界面按鈕
        tvToRegister = (TextView) findViewById(R.id.tvToRegister);
        tvToRegister.setOnClickListener(this);
    }

//    按鈕效果副函式
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.loginButton:
                if(loginUsername.getText().toString().equals("iclab") && loginPassword.getText().toString().equals("aa")){
                          Toast.makeText(LoginActivity.this,"Login Successful!", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(LoginActivity.this, MainActivity.class));
                      }
                else{
                    Toast.makeText(LoginActivity.this,"Incorrect username or password!", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.tvToRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}