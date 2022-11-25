package com.example.finlis_20_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDatabaseHelper extends SQLiteOpenHelper {

    static final String DatabaseName = "FinLisAccountDatabase";
    static final int DatabaseVersion = 2;
    static final String ACCOUNT_TABLE = "accounts";
    static final String ACCOUNT_ID = "id";
    static final String ACCOUNT_USERNAME = "username";
    static final String ACCOUNT_PASSWORD = "password";

    public AccountDatabaseHelper(Context context){
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL = "create table " + ACCOUNT_TABLE + "("
                + ACCOUNT_ID + " integer primary key autoincrement, "
                + ACCOUNT_USERNAME + " text, "
                + ACCOUNT_PASSWORD + " text )";
        sqLiteDatabase.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + ACCOUNT_TABLE);
        onCreate(sqLiteDatabase);
    }
}