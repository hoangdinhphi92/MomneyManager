package com.example.momney.manager.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.momney.manager.data.MoneyEntry;

import java.util.ArrayList;
import java.util.List;

public class MoneyDatabaseImpl extends SQLiteOpenHelper implements MoneyDatabase {
    private static final int TYPE_ALL = 0;
    private static final int TYPE_INCOME = 1;
    private static final int TYPE_EXPENSE = 2;

    public static final String TABLE_NAME = "money";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String DATABASE_NAME = "money.db";
    public static final int DATABASE_VESION = 1;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public MoneyDatabaseImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_AMOUNT + " INTEGER," +
                COLUMN_TIME + " INTEGER," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public List<MoneyEntry> getTransactions() {
        return getTransactions(0, System.currentTimeMillis());
    }

    @Override
    public List<MoneyEntry> getTransactions(long startTime, long endTime) {
        return getTransactions(TYPE_ALL, startTime, endTime);
    }

    @Override
    public List<MoneyEntry> getIncomes() {
        return getIncomes(0, System.currentTimeMillis());
    }

    @Override
    public List<MoneyEntry> getIncomes(long startTime, long endTime) {
        return getTransactions(TYPE_INCOME, startTime, endTime);
    }

    @Override
    public List<MoneyEntry> getExpense() {
        return getExpense(0, System.currentTimeMillis());
    }

    @Override
    public List<MoneyEntry> getExpense(long startTime, long endTime) {
        return getTransactions(TYPE_EXPENSE, startTime, endTime);
    }

    private List<MoneyEntry> getTransactions(int type, long startTime, long endTime) {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append("FROM " + TABLE_NAME + " ");
        query.append("WHERE (" + COLUMN_TIME + " BETWEEN ").append(startTime).append(" and ").append(endTime).append(") ");
        if (type == TYPE_INCOME)
            query.append("AND " + COLUMN_AMOUNT + "> 0 ");
        else if (type == TYPE_EXPENSE)
            query.append("AND " + COLUMN_AMOUNT + "< 0 ");

        query.append("ORDER BY " + COLUMN_TIME);

        List<MoneyEntry> entries = new ArrayList<>();
        Cursor cursor = db.rawQuery(query.toString(), null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int columnId = cursor.getColumnIndex(COLUMN_ID);
                    int columnAmount = cursor.getColumnIndex(COLUMN_AMOUNT);
                    int columnTime = cursor.getColumnIndex(COLUMN_TIME);
                    int columnContent = cursor.getColumnIndex(COLUMN_CONTENT);
                    int columnDes = cursor.getColumnIndex(COLUMN_DESCRIPTION);

                    MoneyEntry moneyEntry = new MoneyEntry(
                            cursor.getInt(columnId),
                            cursor.getInt(columnAmount),
                            cursor.getLong(columnTime),
                            cursor.getString(columnContent),
                            cursor.getString(columnDes)
                    );
                    entries.add(moneyEntry);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return entries;
    }

    @Override
    public long total() {
        return total(0, System.currentTimeMillis());
    }

    @Override
    public long total(long startTime, long endTime) {
        return total(TYPE_ALL, startTime, endTime);
    }

    @Override
    public long totalIncome() {
        return totalIncome(0, System.currentTimeMillis());
    }

    @Override
    public long totalIncome(long startTime, long endTime) {
        return total(TYPE_INCOME, startTime, endTime);
    }

    @Override
    public long totalExpense() {
        return totalExpense(0, System.currentTimeMillis());
    }

    @Override
    public long totalExpense(long startTime, long endTime) {
        return total(TYPE_EXPENSE, startTime, endTime);
    }

    private long total(int type, long startTime, long endTime) {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT SUM(" + COLUMN_AMOUNT + ") ");
        query.append("FROM " + TABLE_NAME + " ");
        query.append("WHERE (" + COLUMN_TIME + " BETWEEN ").append(startTime).append(" and ").append(endTime).append(") ");
        if (type == TYPE_INCOME)
            query.append("AND " + COLUMN_AMOUNT + "> 0 ");
        else if (type == TYPE_EXPENSE)
            query.append("AND " + COLUMN_AMOUNT + "< 0 ");

        Cursor cursor = db.rawQuery(query.toString(), null);
        if (cursor != null) {
            if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
                return cursor.getInt(0);
            }
            cursor.close();
        }
        return 0;
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
        values.put(COLUMN_CONTENT, money.getContent());

        // Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void update(MoneyEntry money) {
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, money.getAmount());
        values.put(COLUMN_DESCRIPTION, money.getDescription());
        values.put(COLUMN_TIME, money.getTime());
        values.put(COLUMN_CONTENT, money.getContent());

        // Insert the new row, returning the primary key value of the new row
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(money.getId())});
    }

    @Override
    public void delete(MoneyEntry money) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(money.getId())});
    }
}
