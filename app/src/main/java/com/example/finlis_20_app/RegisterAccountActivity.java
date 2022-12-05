package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterAccountActivity extends AppCompatActivity implements View.OnClickListener {

    EditText registerUsername, registerPassword;
    Button registerButton, accountmainButton;
    TextView tvToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsername = (EditText) findViewById(R.id.registerUsername);
        registerPassword = (EditText) findViewById(R.id.registerPassword);

        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        accountmainButton = (Button)findViewById(R.id.accountmainButton);
        accountmainButton.setOnClickListener(this);

        tvToLogin = (TextView)findViewById(R.id.tvToLogin);
        tvToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvToLogin:
                startActivity(new Intent(this, LoginAccountActivity.class));
                break;

            case R.id.registerButton:
                saveContact();
                break;

            case R.id.accountmainButton:
                startActivity(new Intent(this, MainAccountActivity.class));
                break;
        }
    }

    public void saveContact(){
        SQLiteDatabase sqLiteDatabase = new DatabaseHelperAccount(this).getWritableDatabase();
        ContentValues values = new ContentValues();

        String s1 = registerUsername.getText().toString().trim();
        String s2 = registerPassword.getText().toString().trim();

        if(TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2)){
            Toast.makeText(this,"Fill the blanks!!", Toast.LENGTH_SHORT).show();
        }
        else if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2)){
            values.put(DatabaseHelperAccount.ACCOUNT_USERNAME, registerUsername.getText().toString());
            values.put(DatabaseHelperAccount.ACCOUNT_PASSWORD, registerPassword.getText().toString());
            sqLiteDatabase.insert(DatabaseHelperAccount.ACCOUNT_TABLE.toString(), null, values);
            sqLiteDatabase.close();
            startActivity(new Intent(this, LoginAccountActivity.class));
            Toast.makeText(RegisterAccountActivity.this, "Register Successful!", Toast.LENGTH_SHORT).show();
        }
    }
}

