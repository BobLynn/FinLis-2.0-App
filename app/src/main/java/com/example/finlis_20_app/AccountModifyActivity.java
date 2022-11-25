package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AccountModifyActivity extends AppCompatActivity {

    String id;
    EditText modifyUsername, modifyPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_modify);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        id = bundle.getString("id", "0");
        modifyUsername = (EditText) findViewById(R.id.modifyUsername);
        modifyPassword = (EditText) findViewById(R.id.modifyPassword);
        modifyUsername.setText(bundle.getString("username"));
        modifyPassword.setText(bundle.getString("password"));
    }


    public void actionPerformed(View view) {
        SQLiteDatabase sqLiteDatabase;

        switch (view.getId()){
            case R.id.modifyModifyButton:
                sqLiteDatabase = new AccountDatabaseHelper(this).getWritableDatabase();
                String sqlUpdate = "update " +
                        AccountDatabaseHelper.ACCOUNT_TABLE +
                        " set " + AccountDatabaseHelper.ACCOUNT_USERNAME +
                        "='" + modifyUsername.getText().toString() + "', " +
                        AccountDatabaseHelper.ACCOUNT_PASSWORD + "='" +
                        modifyPassword.getText().toString() +"' " +
                        "where " + AccountDatabaseHelper.ACCOUNT_ID +
                        "=" + id;
                sqLiteDatabase.execSQL(sqlUpdate);
                sqLiteDatabase.close();
                Toast.makeText(this, "Account Content Modified Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AccountMainActivity.class));
                break;

            case R.id.modifyDeleteButton:
                sqLiteDatabase = new AccountDatabaseHelper(this).getWritableDatabase();

                String sqlDelete = "delete from " +
                        AccountDatabaseHelper.ACCOUNT_TABLE +
                        " where " + AccountDatabaseHelper.ACCOUNT_ID + "=" +
                        id;

                sqLiteDatabase.execSQL(sqlDelete);
                sqLiteDatabase.close();
                Toast.makeText(this, "Delete Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AccountMainActivity.class));

                break;
            case R.id.modifyBackToContentButton:
                startActivity(new Intent(AccountModifyActivity.this, AccountMainActivity.class));
        }
    }
}