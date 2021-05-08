package com.example.momney.manager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class MoneyDatabaseImpl extends SQLiteOpenHelper implements MoneyDatabase {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String DATABASE_NAME = "money.db";
    public static final int DATABASE_VESION = 1;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public MoneyDatabaseImpl(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESION);
    }

    @Override
    public List<MoneyEntry> getAllTransactions() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + DATABASE_NAME, null);
        List<MoneyEntry> moneyEntries = new ArrayList<MoneyEntry>();
        while (moneyDB.moveToNext()){
            MoneyEntry moneyEntry = new MoneyEntry(moneyDB.getInt(0), moneyDB.getInt(1),
                    moneyDB.getInt(2), moneyDB.getString(3));
            moneyEntries.add(moneyEntry);

        }
        moneyDB.close();
        return moneyEntries;
    }

    @Override
    public void insert(MoneyEntry money) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, money.getAmount());
        values.put(COLUMN_DESCRIPTION, money.getDescription());
        values.put(COLUMN_TIME, money.getTime());

        // Insert the new row, returning the primary key value of the new row
        db.insert(DATABASE_NAME, null, values);
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
                COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_AMOUNT+ " INTEGER," +
                COLUMN_TIME + " INTEGER," +
                COLUMN_DESCRIPTION + "description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
