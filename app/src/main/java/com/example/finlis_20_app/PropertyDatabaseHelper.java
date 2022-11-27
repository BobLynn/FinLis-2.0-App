package com.example.finlis_20_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PropertyDatabaseHelper extends SQLiteOpenHelper {

    static final String DatabaseName = "FinLisPropertyDatabase";
    static final int DatabaseVersion = 3;
    static final String PROPERTY_TABLE = "property";
    static final String PROPERTY_ID = "id";
    static final String PROPERTY_NAME = "name";
    static final String PROPERTY_CONTENT = "content";

    public PropertyDatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override

//    切記：中間的字符一定在前面要有空格！！
    public void onCreate(SQLiteDatabase propertyDatabase) {
        String sql = "create table " + PROPERTY_TABLE + " ( "
                + PROPERTY_ID + " integer primary key autoincrement, "
                + PROPERTY_NAME + " text, "
                + PROPERTY_CONTENT + " text )";
        propertyDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase propertyDatabase, int i, int i1) {
        propertyDatabase.execSQL("drop table if exists " + PROPERTY_TABLE);
        onCreate(propertyDatabase);
    }
}
