package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button propertyButton, toOCRButton;

    Button logoutButton;
    Button accountmainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        財產界面按鈕
        propertyButton = (Button)findViewById(R.id.propertyButton);
        propertyButton.setOnClickListener(this);

//        光學字元辨識按鈕
        toOCRButton = (Button)findViewById(R.id.toOCRButton);
        toOCRButton.setOnClickListener(this);

//        登出按鈕
        logoutButton = (Button)findViewById(R.id.logoutBotton);
        logoutButton.setOnClickListener(this);

//        賬戶列表界面按鈕
        accountmainButton = (Button) findViewById(R.id.accountmainButton);
        accountmainButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.propertyButton:
                startActivity(new Intent(this, AddPropertyActivity.class));
                break;

            case R.id.toOCRButton:
                startActivity(new Intent(this, MainOCRActivity.class));
                break;

            case R.id.logoutBotton:
                startActivity(new Intent(this, LoginAccountActivity.class));
                Toast.makeText(MainActivity.this, "Logout Successful!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.accountmainButton:
                startActivity(new Intent(this, MainAccountActivity.class));
                Toast.makeText(this, "Magic in the air!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}