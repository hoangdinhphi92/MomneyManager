package com.example.momney.manager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MoneyDatabaseImpl extends SQLiteOpenHelper implements MoneyDatabase {

    public static final String DATABASE_NAME = "money.db";
    public static final int DATABASE_VESION = 1;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public MoneyDatabaseImpl(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESION);
    }

    @Override
    public void insert(MoneyEntry money) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + DATABASE_NAME + " VALUES (" +
                "null, "+ money.getAmount() + ", " +
                money.getTime() + ", '" + money.getDescription() + "')");
    }

    @Override
    public void update(MoneyEntry money) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+ DATABASE_NAME +" SET " +
                "amount ="+ money.getAmount() + ", time = " + money.getTime() +
                " description = '" + money.getDescription() + "' WHERE id = " + money.getId() );
    }

    @Override
    public void delete(MoneyEntry money) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + DATABASE_NAME + " WHERE id = " + money.getId());
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
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
