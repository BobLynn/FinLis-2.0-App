package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddPropertyActivity extends AppCompatActivity implements View.OnClickListener{

    TextView propertyName, propertyContent, propertyUserName;
    Button uploadDataButton;
    Button pasteUserName;
    Button toPropertyContentListButton;
    Button toMainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_add);

        propertyName = findViewById(R.id.propertyName);
        propertyContent = findViewById(R.id.propertyContent);
        propertyUserName = findViewById(R.id.propertyUserName);

        uploadDataButton = findViewById(R.id.uploadDataButton);
        uploadDataButton.setOnClickListener(this);

        pasteUserName = findViewById(R.id.pasteUserName);
        pasteUserName.setOnClickListener(this);

        toPropertyContentListButton  = findViewById(R.id.toPropertyContentListButton);
        toPropertyContentListButton.setOnClickListener(this);

        toMainMenuButton = findViewById(R.id.toMainMenuButton);
        toMainMenuButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadDataButton:
                saveProperty();

                break;

            case R.id.pasteUserName:
                String userid = getSharedPreferences("userdata", MODE_PRIVATE).getString("USER","");
                propertyUserName.setText(userid);
                break;

            case R.id.toPropertyContentListButton:
                startActivity(new Intent(this, MainPropertyActivity.class));
                break;

            case R.id.toMainMenuButton:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

    public void saveProperty(){
        SQLiteDatabase sqLiteDatabase = new DatabaseHelperProperty(this).getWritableDatabase();
        ContentValues values = new ContentValues();

        String k1 = propertyName.getText().toString().trim();
        String k2 = propertyContent.getText().toString().trim();
        String k3 = propertyUserName.getText().toString().trim();

        if(TextUtils.isEmpty(k1) || TextUtils.isEmpty(k2) || TextUtils.isEmpty(k3)) {
            Toast.makeText(this,"Fill the blanks!!", Toast.LENGTH_SHORT).show();

        }
        else if (!TextUtils.isEmpty(k1) && !TextUtils.isEmpty(k2) && !TextUtils.isEmpty(k3)){
            values.put(DatabaseHelperProperty.PROPERTY_NAME, propertyName.getText().toString());
            values.put(DatabaseHelperProperty.PROPERTY_CONTENT, propertyContent.getText().toString());
            values.put(DatabaseHelperProperty.PROPERTY_USERNAME, propertyUserName.getText().toString());
            sqLiteDatabase.insert(DatabaseHelperProperty.PROPERTY_TABLE.toString(), null, values);
            sqLiteDatabase.close();
            Toast.makeText(this, "Property Content Successfully Added! ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainPropertyActivity.class));
        }


    }
}