package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PropertyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_main);

        SQLiteDatabase propertyDatabase = new DatabaseHelperProperty(this).getReadableDatabase();

        String sql = "select " +
                DatabaseHelperProperty.PROPERTY_ID + " , " +
                DatabaseHelperProperty.PROPERTY_NAME + " , " +
                DatabaseHelperProperty.PROPERTY_CONTENT + " from " +
                DatabaseHelperProperty.PROPERTY_TABLE;

        Cursor cursor = propertyDatabase.rawQuery(sql, null);

        final ArrayList<Map<String, String>> list = new ArrayList<>();

        if(cursor.getCount() != 0){
            cursor.moveToLast();
            for(int j = 0; j< cursor.getCount(); j++){
                Map<String, String> map = new HashMap<String, String>();
                map.put(DatabaseHelperProperty.PROPERTY_ID, cursor.getString(0));
                map.put(DatabaseHelperProperty.PROPERTY_NAME, cursor.getString(1));
                map.put(DatabaseHelperProperty.PROPERTY_CONTENT, cursor.getString(2));
                list.add(map);
                cursor.moveToPrevious();
            }
        }
        cursor.close();
        propertyDatabase.close();

        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[]{DatabaseHelperProperty.PROPERTY_NAME, DatabaseHelperProperty.PROPERTY_CONTENT},
                new int[]{android.R.id.text1, android.R.id.text2});

        ListView listView = findViewById(R.id.propertyContentListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", list.get(position).get(DatabaseHelperProperty.PROPERTY_ID));
                intent.putExtra("name", list.get(position).get(DatabaseHelperProperty.PROPERTY_NAME));
                intent.putExtra("content", list.get(position).get(DatabaseHelperProperty.PROPERTY_CONTENT));
                intent.setClass(PropertyMainActivity.this, ModifyPropertyActivity.class);
                startActivity(intent);
            }
        });

    }

    public void faToAddProperty (View view){
        startActivity(new Intent(this, AddPropertyActivity.class));
    }

    public void faToMainMenu(View view){
        startActivity(new Intent(this, AddPropertyActivity.class));
    }
}