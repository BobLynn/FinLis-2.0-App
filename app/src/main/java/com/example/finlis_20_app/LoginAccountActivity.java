package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginAccountActivity extends AppCompatActivity implements View.OnClickListener{

    EditText loginUsername;
    EditText loginPassword;
    String readUsername, readPassword;
    Button loginButton;
    Button accountmainButton;
    TextView tvToRegister;
    Button toMainMenuButton;
    Button toMySQLButton;


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

//        到主畫面按鈕
        toMainMenuButton = (Button) findViewById(R.id.toMainMenuButton);
        toMainMenuButton.setOnClickListener(this);

//        MySQL測試界面按鈕
        toMySQLButton = (Button)findViewById(R.id.toMySQLButton);
        toMySQLButton.setOnClickListener(this);
    }

//    按鈕效果副函式
    @Override
    public void onClick(View view) {

//        讀取資料庫資料
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = new DatabaseHelperAccount(this).getWritableDatabase();
        Cursor readDatabase = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHelperAccount.ACCOUNT_TABLE,null);

        switch (view.getId()){

            case R.id.loginButton:

                String j1 = loginUsername.getText().toString().trim();
                String j2 = loginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(j1) || TextUtils.isEmpty(j2)){
                    Toast.makeText(LoginAccountActivity.this,"Fill the blanks!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    while(readDatabase.moveToNext()) {
                        readUsername = readDatabase.getString(1);
                        readPassword = readDatabase.getString(2);

                        if(loginUsername.getText().toString().equals(readUsername) && loginPassword.getText().toString().equals(readPassword)){
                            Toast.makeText(LoginAccountActivity.this,"Login Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginAccountActivity.this, MainActivity.class));

                            //將目前登入資料寫入SharedPreferences
                            String saveUsername = loginUsername.getText().toString();
                            SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
                            pref.edit().putString("USER", saveUsername).commit();
                        }
                        else {
                            Toast.makeText(LoginAccountActivity.this,"Incorrect username or password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;

            //(TextView)進入註冊界面按鈕
            case R.id.tvToRegister:
                startActivity(new Intent(this, RegisterAccountActivity.class));
                Toast.makeText(this, "Insert ID and PW to finish registration!!", Toast.LENGTH_SHORT).show();
                break;

            //(Button)進入賬戶資料庫界面按鈕
            case R.id.accountmainButton:
                startActivity(new Intent(this, MainAccountActivity.class));
                Toast.makeText(this, "Magic in the air!", Toast.LENGTH_SHORT).show();
                break;

            //(Button)進入主畫面按鈕
            case R.id.toMainMenuButton:
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Magic in the air!", Toast.LENGTH_SHORT).show();
                break;

            //(Buuton)進入MySQL測試界面按鈕
            case R.id.toMySQLButton:
                startActivity(new Intent(this, MySQLTestActivity.class));
                Toast.makeText(this, "Magic in the air!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}