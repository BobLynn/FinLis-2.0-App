package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView registerUsername, registerPassword;
    Button registerButton, accountmainButton;
    TextView tvToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsername = (TextView)findViewById(R.id.registerUsername);
        registerPassword = (TextView)findViewById(R.id.registerPassword);

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
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.registerButton:
                saveContact();
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(RegisterActivity.this, "Register Successful!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.accountmainButton:
                startActivity(new Intent(this, AccountMainActivity.class));


        }
    }

    public void saveContact(){
        SQLiteDatabase sqLiteDatabase = new MyDatabaseHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.ACCOUNT_USERNAME, registerUsername.getText().toString());
        values.put(MyDatabaseHelper.ACCOUNT_PASSWORD, registerPassword.getText().toString());
        sqLiteDatabase.insert(MyDatabaseHelper.ACCOUNT_TABLE.toString(), null, values);
        sqLiteDatabase.close();
    }

}

