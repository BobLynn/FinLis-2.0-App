package com.example.finlis_20_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperProperty extends SQLiteOpenHelper {

    static final String DatabaseName = "FinLisPropertyDatabase";
    static final int DatabaseVersion = 4;
    static final String PROPERTY_TABLE = "property";
    static final String PROPERTY_ID = "id";
    static final String PROPERTY_NAME = "name";
    static final String PROPERTY_CONTENT = "content";
    static final String PROPERTY_USERNAME = "username_property";

    public DatabaseHelperProperty(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }


    //  切記：中間的字符一定在前面要有空格！！
    @Override
    public void onCreate(SQLiteDatabase propertyDatabase) {
        String sql = "create table " + PROPERTY_TABLE + " ( "
                + PROPERTY_ID + " integer primary key autoincrement, "
                + PROPERTY_NAME + " text, "
                + PROPERTY_CONTENT + " text, " + PROPERTY_USERNAME + " text )";
        propertyDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase propertyDatabase, int i, int i1) {
        propertyDatabase.execSQL("drop table if exists " + PROPERTY_TABLE);
        onCreate(propertyDatabase);
    }
}
