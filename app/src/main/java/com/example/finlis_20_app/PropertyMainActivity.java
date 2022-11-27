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

        SQLiteDatabase propertyDatabase = new PropertyDatabaseHelper(this).getReadableDatabase();

        String sql = "select " +
                PropertyDatabaseHelper.PROPERTY_ID + " , " +
                PropertyDatabaseHelper.PROPERTY_NAME + " , " +
                PropertyDatabaseHelper.PROPERTY_CONTENT + " from " +
                PropertyDatabaseHelper.PROPERTY_TABLE;

        Cursor cursor = propertyDatabase.rawQuery(sql, null);

        final ArrayList<Map<String, String>> list = new ArrayList<>();

        if(cursor.getCount() != 0){
            cursor.moveToLast();
            for(int j = 0; j< cursor.getCount(); j++){
                Map<String, String> map = new HashMap<String, String>();
                map.put(PropertyDatabaseHelper.PROPERTY_ID, cursor.getString(0));
                map.put(PropertyDatabaseHelper.PROPERTY_NAME, cursor.getString(1));
                map.put(PropertyDatabaseHelper.PROPERTY_CONTENT, cursor.getString(2));
                list.add(map);
                cursor.moveToPrevious();
            }
        }
        cursor.close();
        propertyDatabase.close();

        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[]{PropertyDatabaseHelper.PROPERTY_NAME, PropertyDatabaseHelper.PROPERTY_CONTENT},
                new int[]{android.R.id.text1, android.R.id.text2});

        ListView listView = findViewById(R.id.propertyContentListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", list.get(position).get(PropertyDatabaseHelper.PROPERTY_ID));
                intent.putExtra("name", list.get(position).get(PropertyDatabaseHelper.PROPERTY_NAME));
                intent.putExtra("content", list.get(position).get(PropertyDatabaseHelper.PROPERTY_CONTENT));
                intent.setClass(PropertyMainActivity.this, PropertyModifyActivity.class);
                startActivity(intent);
            }
        });

    }
}