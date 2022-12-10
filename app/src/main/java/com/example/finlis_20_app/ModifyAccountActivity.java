package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyAccountActivity extends AppCompatActivity {

    String id;
    EditText modifyUsername, modifyPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_modify);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        modifyUsername = findViewById(R.id.modifyUsername);
        modifyPassword = findViewById(R.id.modifyPassword);

        id = bundle.getString("id", "0");

        modifyUsername.setText(bundle.getString("username"));
        modifyPassword.setText(bundle.getString("password"));
    }


    public void actionPerformed(View view) {
        SQLiteDatabase sqLiteDatabase;

        switch (view.getId()){
            case R.id.modifyModifyButton:
                sqLiteDatabase = new DatabaseHelperAccount(this).getWritableDatabase();
                String sqlUpdate = "update " +
                        DatabaseHelperAccount.ACCOUNT_TABLE +
                        " set " + DatabaseHelperAccount.ACCOUNT_USERNAME +
                        "='" + modifyUsername.getText().toString() + "', " +
                        DatabaseHelperAccount.ACCOUNT_PASSWORD + "='" +
                        modifyPassword.getText().toString() +"' " +
                        "where " + DatabaseHelperAccount.ACCOUNT_ID +
                        "=" + id;
                sqLiteDatabase.execSQL(sqlUpdate);
                sqLiteDatabase.close();
                Toast.makeText(this, "Account Content Modified Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainAccountActivity.class));
                break;

            case R.id.modifyDeleteButton:
                sqLiteDatabase = new DatabaseHelperAccount(this).getWritableDatabase();

                String sqlDelete = "delete from " +
                        DatabaseHelperAccount.ACCOUNT_TABLE +
                        " where " + DatabaseHelperAccount.ACCOUNT_ID + "=" +
                        id;

                sqLiteDatabase.execSQL(sqlDelete);
                sqLiteDatabase.close();
                Toast.makeText(this, "Delete Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainAccountActivity.class));

                break;
            case R.id.modifyBackToContentButton:
                startActivity(new Intent(ModifyAccountActivity.this, MainAccountActivity.class));


        }
    }
}