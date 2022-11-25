package com.example.finlis_20_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PropertyDatabaseHelper extends SQLiteOpenHelper {

    static final String DatabaseName = "FinLisPropertyDatabase";
    static final int DatabaseVersion = 1;
    static final String PROPERTY_TABLE = "property";
    static final String PROPERTY_ID = "id";
    static final String PROPERTY_NAME = "name";
    static final String PROPERTY_CONTENT = "content";

    public PropertyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase propertyDatabase) {
        String SQL  = "create table " + PROPERTY_TABLE + " ( "
                + PROPERTY_ID + "integer primary key autoincrement, "
                + PROPERTY_NAME + "text, "
                + PROPERTY_CONTENT + "text )";
        propertyDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase propertyDatabase, int i, int i1) {
        propertyDatabase.execSQL("drop table if exists " + PROPERTY_TABLE);
        onCreate(propertyDatabase);
    }
}
