package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    Button accountmainButton;
    TextView tvToRegister;
    String readUsername, readPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        賬戶名和密碼
        loginUsername = (EditText)findViewById(R.id.modifyUsername);
        loginPassword = (EditText)findViewById(R.id.modifyPassword);

//        登入按鈕
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

//        註冊界面按鈕
        tvToRegister = (TextView) findViewById(R.id.tvToRegister);
        tvToRegister.setOnClickListener(this);

//        賬戶列表界面按鈕
        accountmainButton = (Button) findViewById(R.id.accountmainButton);
        accountmainButton.setOnClickListener(this);
    }

//    按鈕效果副函式
    @Override
    public void onClick(View view) {

//        讀取資料庫資料
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = new AccountDatabaseHelper(this).getWritableDatabase();
        Cursor readDatabase = sqLiteDatabase.rawQuery("SELECT * FROM accounts",null);

        switch (view.getId()){

            case R.id.loginButton:

                while(readDatabase.moveToNext()){
                    readUsername = readDatabase.getString(1);
                    readPassword = readDatabase.getString(2);
                    if(loginUsername.getText().toString().equals(readUsername) && loginPassword.getText().toString().equals(readPassword)){
                        Toast.makeText(LoginActivity.this,"Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        break;
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Incorrect username or password!", Toast.LENGTH_SHORT).show();
                    }

                }


                break;

            case R.id.tvToRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.accountmainButton:
                startActivity(new Intent(this, AccountMainActivity.class));
                break;
        }
    }
}