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

public class AccountMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_main);
//        getReadableDatabase: 先以讀寫方式打開數據庫，如果數據庫的磁盤空間滿了，
//        就會打開失敗，當打開失敗後會繼續嘗試以只讀方式打開數據庫。
//        如果該問題成功解決，則只讀數據庫對象就會關閉，然後返回一個可讀寫的數據庫對象
        SQLiteDatabase sqLiteDatabase = new DatabaseHelperAccount(this).getReadableDatabase();

        String SQL = "select " +
                DatabaseHelperAccount.ACCOUNT_ID + " , " +
                DatabaseHelperAccount.ACCOUNT_USERNAME + " , " +
                DatabaseHelperAccount.ACCOUNT_PASSWORD + " from " +
                DatabaseHelperAccount.ACCOUNT_TABLE;

        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);

        final ArrayList<Map<String, String >> list = new ArrayList<>();

        if(cursor.getCount() != 0){
            cursor.moveToLast();
            for (int i = 0; i<cursor.getCount(); i++){
                Map<String, String> map = new HashMap<String, String>();
                map.put(DatabaseHelperAccount.ACCOUNT_ID, cursor.getString(0));
                map.put(DatabaseHelperAccount.ACCOUNT_USERNAME, cursor.getString(1));
                map.put(DatabaseHelperAccount.ACCOUNT_PASSWORD, cursor.getString(2));
                list.add(map);
                cursor.moveToPrevious();
            }
        }
        cursor.close();
        sqLiteDatabase.close();

        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[]{DatabaseHelperAccount.ACCOUNT_USERNAME, DatabaseHelperAccount.ACCOUNT_PASSWORD},
                new int[]{android.R.id.text1, android.R.id.text2});


        ListView listView = findViewById(R.id.accountContentListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", list.get(position).get(DatabaseHelperAccount.ACCOUNT_ID));
                intent.putExtra("username", list.get(position).get(DatabaseHelperAccount.ACCOUNT_USERNAME));
                intent.putExtra("password", list.get(position).get(DatabaseHelperAccount.ACCOUNT_PASSWORD));
                intent.setClass(AccountMainActivity.this, ModifyAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    //「+」按鈕動作
    public void faToRegister(View view) {
        Intent intent = new Intent();
        intent.setClass(this, RegisterAccountActivity.class);
        startActivity(intent);
    }

    public void faToLogin(View view){
        startActivity(new Intent(AccountMainActivity.this, LoginAccountActivity.class));
    }


}