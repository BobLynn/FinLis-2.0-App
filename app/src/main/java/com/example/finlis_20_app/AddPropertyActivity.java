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

    TextView propertyName, propertyContent;

    Button uploadDataButton;
    Button toPropertyContentListButton;
    Button toMainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_add);

        propertyName = (TextView)findViewById(R.id.propertyName);
        propertyContent = (TextView)findViewById(R.id.propertyContent);

        uploadDataButton = (Button)findViewById(R.id.uploadDataButton);
        uploadDataButton.setOnClickListener(this);

        toPropertyContentListButton  = (Button)findViewById(R.id.toPropertyContentListButton);
        toPropertyContentListButton.setOnClickListener(this);

        toMainMenuButton = (Button)findViewById(R.id.toMainMenuButton);
        toMainMenuButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadDataButton:
                saveProperty();
                break;

            case R.id.toPropertyContentListButton:
                startActivity(new Intent(this, PropertyMainActivity.class));
                break;

            case R.id.toMainMenuButton:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

    public void saveProperty(){
        SQLiteDatabase sqLiteDatabase = new DatabaseHelperProperty(this).getWritableDatabase();
        ContentValues values = new ContentValues();

        String i1 = propertyName.getText().toString().trim();
        String i2 = propertyContent.getText().toString().trim();

        if(TextUtils.isEmpty(i1) || TextUtils.isEmpty(i2)) {
            Toast.makeText(this,"Fill the blanks!!", Toast.LENGTH_SHORT).show();
        }else if (!TextUtils.isEmpty(i1) && !TextUtils.isEmpty(i2)){
            values.put(DatabaseHelperProperty.PROPERTY_NAME, propertyName.getText().toString());
            values.put(DatabaseHelperProperty.PROPERTY_CONTENT, propertyContent.getText().toString());
            sqLiteDatabase.insert(DatabaseHelperProperty.PROPERTY_TABLE.toString(), null, values);
            sqLiteDatabase.close();
            Toast.makeText(this, "Property Content Successfully Added! ", Toast.LENGTH_SHORT).show();
        }


    }
}