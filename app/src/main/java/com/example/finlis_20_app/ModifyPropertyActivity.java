package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyPropertyActivity extends AppCompatActivity {


    String id;
    EditText modifyPropertyName, modifyPropertyContent;
//    Button modifyModifyButton, modifyDeleteButton, modifyBackToContentButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_modify);

        Intent intent = getIntent();
        Bundle bundle_1 = intent.getExtras();

        id = bundle_1.getString("id", "0");
        modifyPropertyName = (EditText)findViewById(R.id.modifyPropertyName);
        modifyPropertyContent = (EditText)findViewById(R.id.modifyPropertyContent);
        modifyPropertyName.setText(bundle_1.getString("name"));
        modifyPropertyContent.setText(bundle_1.getString("content"));
        }

    public void actionCheck(View v){
        SQLiteDatabase propertyDatabase;

        switch (v.getId()){
            case R.id.modifyModifyButton:
                propertyDatabase = new DatabaseHelperProperty(this).getWritableDatabase();
                String sqlUpdate = "update " +
                        DatabaseHelperProperty.PROPERTY_TABLE +
                        " set " + DatabaseHelperProperty.PROPERTY_NAME +
                        "='" + modifyPropertyName.getText().toString() + "', " +
                        DatabaseHelperProperty.PROPERTY_CONTENT + "='" +
                        modifyPropertyContent.getText().toString() +"' " +
                        "where " + DatabaseHelperProperty.PROPERTY_ID +
                        "=" + id;
                propertyDatabase.execSQL(sqlUpdate);
                propertyDatabase.close();

                Toast.makeText(this, "Property Content Modified Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PropertyMainActivity.class));
                break;
            case R.id.modifyDeleteButton:
                propertyDatabase = new DatabaseHelperProperty(this).getWritableDatabase();
                String sqlDelete = "delete from " +
                        DatabaseHelperProperty.PROPERTY_TABLE +
                        " where " + DatabaseHelperProperty.PROPERTY_ID + "=" +
                        id;
                propertyDatabase.execSQL(sqlDelete);
                propertyDatabase.close();
                Toast.makeText(this, "Delete Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PropertyMainActivity.class));
                break;
            case R.id.modifyBackToContentButton:
                startActivity(new Intent(this, AddPropertyActivity.class));
                break;
        }
    }


}