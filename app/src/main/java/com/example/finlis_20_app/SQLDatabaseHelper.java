package com.example.finlis_20_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLDatabaseHelper extends SQLiteOpenHelper {

    private static final String DatabaseName = "FinLisSQLDatabase";
    private static final int DatabaseVersion = 1;

    public SQLDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String TableName) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS Users (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username text not null," +
                "Password TEXT not null" +
                ")";
        sqLiteDatabase.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE Users";
        sqLiteDatabase.execSQL(SQL);
    }
}
