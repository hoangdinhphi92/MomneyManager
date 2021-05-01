package com.example.momney.manager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class MoneyDatabaseImpl extends SQLiteOpenHelper implements MoneyDatabase {

    public static final String DATABASE_NAME = "money.db";

    public MoneyDatabaseImpl(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void insert(MoneyEntry money) {

    }

    @Override
    public void update(MoneyEntry money) {

    }

    @Override
    public void delete(MoneyEntry money) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "amount INTEGER," +
                "time INTEGER," +
                "description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
