package com.example.momney.manager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.momney.manager.utils.Utils;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.momney.manager.screen.wallet.WalletFragment.FILTER_ALL;
import static com.example.momney.manager.screen.wallet.WalletFragment.FILTER_MONTH;
import static com.example.momney.manager.screen.wallet.WalletFragment.FILTER_WEEK;


public class MoneyDatabaseImpl extends SQLiteOpenHelper implements MoneyDatabase {
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
    public List<MoneyEntry> getAllTransactions() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + TABLE_NAME+ " ORDER BY (" + COLUMN_TIME + ") DESC", null);
        List<MoneyEntry> moneyEntries = new ArrayList<MoneyEntry>();
        while (moneyDB.moveToNext()){
            int columnId = moneyDB.getColumnIndex(COLUMN_ID);
            int columnAmount = moneyDB.getColumnIndex(COLUMN_AMOUNT);
            int columnTime = moneyDB.getColumnIndex(COLUMN_TIME);
            int columnContent = moneyDB.getColumnIndex(COLUMN_CONTENT);
            int columnDes = moneyDB.getColumnIndex(COLUMN_DESCRIPTION);

            MoneyEntry moneyEntry = new MoneyEntry(
                    moneyDB.getInt(columnId),
                    moneyDB.getInt(columnAmount),
                    moneyDB.getLong(columnTime),
                    moneyDB.getString(columnContent),
                    moneyDB.getString(columnDes)
            );
            moneyEntries.add(moneyEntry);

        }
        moneyDB.close();
        return moneyEntries;
    }

    @Override
    public int getAllIncome() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT SUM("+COLUMN_AMOUNT+") FROM "+ TABLE_NAME + " WHERE "+COLUMN_AMOUNT+">0", null);
        cursor.moveToNext();
        int income = cursor.getInt(0);
        cursor.close();
        return income;
    }

    @Override
    public int getAllExpense() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT SUM("+COLUMN_AMOUNT+") FROM "+ TABLE_NAME + " WHERE "+COLUMN_AMOUNT+"<0", null);
        cursor.moveToNext();
        int expense= cursor.getInt(0);
        cursor.close();
        return expense*-1;
    }

    @Override
    public ArrayList<Integer> getIncomeEntry(int filter, long date) {
        ArrayList<Integer> entry = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + TABLE_NAME+ " WHERE " + COLUMN_AMOUNT +">0"+
                " ORDER BY (" + COLUMN_TIME + ") DESC", null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        YearMonth yearMonthObject = YearMonth.of(year, month+1 );
        int daysInMonth = yearMonthObject.lengthOfMonth();
        switch (filter){
            case FILTER_ALL:
                for(int i = 0; i < 12; i++){
                    int total = 0;
                    while (moneyDB.moveToNext())
                        if (Utils.getMonth(moneyDB.getLong(2)) == i &&
                                year == Utils.getYear(moneyDB.getLong(2)))
                            total = total + moneyDB.getInt(1);
                    entry.add(total);
                    moneyDB.moveToFirst();
                }
                break;
            case FILTER_MONTH:
                for (int i = 1; i<= daysInMonth; i++){
                    int total = 0;
                    while (moneyDB.moveToNext())
                        if (Utils.getDayOfMonth(moneyDB.getLong(2)) == i &&
                                year == Utils.getYear(moneyDB.getLong(2)) &&
                                month== Utils.getMonth(moneyDB.getLong(2)))
                            total = total + moneyDB.getInt(1);
                    entry.add(total);
                    moneyDB.moveToFirst();
                }
                break;
            case FILTER_WEEK:
                Calendar monday = Calendar.getInstance();
                monday.setTimeInMillis(Utils.firstDayOfWeek(date));
                for (int i = 0; i<=6; i++){
                    int day = monday.get(Calendar.DAY_OF_YEAR);
                    int year1 = monday.get(Calendar.YEAR);
                    int total = 0;
                    while (moneyDB.moveToNext())
                        if (Utils.getDayOfYear(moneyDB.getLong(2)) == day &&
                                year1 == Utils.getYear(moneyDB.getLong(2)) )
                            total = total + moneyDB.getInt(1);
                    entry.add(total);
                    monday.add(Calendar.DATE, 1);
                    moneyDB.moveToFirst();
                }
                break;
        }
        moneyDB.close();
        return entry;
    }

    @Override
    public ArrayList<Integer> getExpenseEntry(int filter, long date) {
        ArrayList<Integer> entry = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + TABLE_NAME+ " WHERE " + COLUMN_AMOUNT +" <0 "+
                " ORDER BY (" + COLUMN_TIME + ") DESC", null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        YearMonth yearMonthObject = YearMonth.of(year, month+1 );
        int daysInMonth = yearMonthObject.lengthOfMonth();
        switch (filter){
            case FILTER_ALL:
                for(int i = 0; i < 12; i++){
                    int total = 0;
                    while (moneyDB.moveToNext())
                        if (Utils.getMonth(moneyDB.getLong(2)) == i &&
                                year == Utils.getYear(moneyDB.getLong(2)))
                            total = total + moneyDB.getInt(1)*-1;
                    entry.add(total);
                    moneyDB.moveToFirst();
                }
                break;
            case FILTER_MONTH:
                for (int i = 1; i<= daysInMonth; i++){
                    int total = 0;
                    while (moneyDB.moveToNext())
                        if (Utils.getDayOfMonth(moneyDB.getLong(2)) == i &&
                                year == Utils.getYear(moneyDB.getLong(2)) &&
                                month== Utils.getMonth(moneyDB.getLong(2)))
                            total = total + moneyDB.getInt(1)*-1;
                    entry.add(total);
                    moneyDB.moveToFirst();
                }
                break;
            case FILTER_WEEK:
                Calendar monday = Calendar.getInstance();
                monday.setTimeInMillis(Utils.firstDayOfWeek(date));
                for (int i = 0; i<=6; i++){
                    int day = monday.get(Calendar.DAY_OF_YEAR);
                    int year1 = monday.get(Calendar.YEAR);
                    int total = 0;
                    while (moneyDB.moveToNext())
                        if (Utils.getDayOfYear(moneyDB.getLong(2)) == day &&
                                year1 == Utils.getYear(moneyDB.getLong(2)) )
                            total = total + moneyDB.getInt(1)*-1;
                    entry.add(total);
                    monday.add(Calendar.DATE, 1);
                    moneyDB.moveToFirst();
                }
                break;
        }
        moneyDB.close();
        return entry;
    }

    @Override
    public int total(long date, int filter) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + TABLE_NAME+ " ORDER BY (" + COLUMN_TIME + ") DESC", null);
        int total=0;
        switch (filter){
            case FILTER_ALL:
                while (moneyDB.moveToNext())
                    if( Utils.getDayOfYear(date)==Utils.getDayOfYear(moneyDB.getLong(2)) &&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_MONTH:
                while (moneyDB.moveToNext())
                    if( Utils.getMonth(date)==Utils.getMonth(moneyDB.getLong(2)) &&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_WEEK:
                while (moneyDB.moveToNext())
                    if( Utils.getWeek(date)==Utils.getWeek(moneyDB.getLong(2))&&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
        }
        moneyDB.close();
        return total;
    }

    @Override
    public int totalUse(long date, int filter) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + TABLE_NAME+ " WHERE " + COLUMN_AMOUNT +"<0"+
                " ORDER BY (" + COLUMN_TIME + ") DESC", null);
        int total=0;
        switch (filter){
            case FILTER_ALL:
                while (moneyDB.moveToNext())
                    if(Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_MONTH:
                while (moneyDB.moveToNext())
                    if( Utils.getMonth(date)==Utils.getMonth(moneyDB.getLong(2)) &&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_WEEK:
                while (moneyDB.moveToNext())
                    if( Utils.getWeek(date)==Utils.getWeek(moneyDB.getLong(2))&&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
        }
        moneyDB.close();
        return total*-1;
    }

    @Override
    public int totalIncome(long date, int filter) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + TABLE_NAME+ " WHERE " + COLUMN_AMOUNT +">0"+
                " ORDER BY (" + COLUMN_TIME + ") DESC", null);
        int total=0;
        switch (filter){
            case FILTER_ALL:
                while (moneyDB.moveToNext())
                    if(Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_MONTH:
                while (moneyDB.moveToNext())
                    if( Utils.getMonth(date)==Utils.getMonth(moneyDB.getLong(2)) &&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_WEEK:
                while (moneyDB.moveToNext())
                    if( Utils.getWeek(date)==Utils.getWeek(moneyDB.getLong(2))&&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
        }
        moneyDB.close();
        return total;
    }

    @Override
    public int totalUseContent(long date, int filter, String content) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + TABLE_NAME+ "" +
                " WHERE " + COLUMN_CONTENT + "= '" + content + "' AND " + COLUMN_AMOUNT + " < 0" +
                " ORDER BY (" + COLUMN_TIME + ") DESC", null);
        int total=0;
        switch (filter){
            case FILTER_ALL:
                while (moneyDB.moveToNext())
                        if(Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                            total = total + moneyDB.getInt(1);
            case FILTER_MONTH:
                while (moneyDB.moveToNext())
                    if( Utils.getMonth(date)==Utils.getMonth(moneyDB.getLong(2)) &&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_WEEK:
                while (moneyDB.moveToNext())
                    if( Utils.getWeek(date)==Utils.getWeek(moneyDB.getLong(2))&&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
        }
        moneyDB.close();
        return total*-1;
    }

    @Override
    public int totalIncomeContent(long date, int filter, String content) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor moneyDB = db.rawQuery(" SELECT* FROM " + TABLE_NAME+ "" +
                " WHERE " + COLUMN_CONTENT + "= '" + content + "' AND " + COLUMN_AMOUNT + " > 0" +
                " ORDER BY (" + COLUMN_TIME + ") DESC", null);
        int total=0;
        switch (filter){
            case FILTER_ALL:
                while (moneyDB.moveToNext())
                    if(Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_MONTH:
                while (moneyDB.moveToNext())
                    if( Utils.getMonth(date)==Utils.getMonth(moneyDB.getLong(2)) &&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
            case FILTER_WEEK:
                while (moneyDB.moveToNext())
                    if( Utils.getWeek(date)==Utils.getWeek(moneyDB.getLong(2))&&
                            Utils.getYear(date)==Utils.getYear(moneyDB.getLong(2)))
                        total = total + moneyDB.getInt(1);
        }
        moneyDB.close();
        return total;
    }


    @Override
    public void deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

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
        int count = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(money.getId())});


    }

    @Override
    public void delete(MoneyEntry money) {
        SQLiteDatabase db = getReadableDatabase();
        int count = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(money.getId())});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_AMOUNT+ " INTEGER," +
                COLUMN_TIME + " INTEGER," +
                COLUMN_CONTENT + " TEXT,"+
                COLUMN_DESCRIPTION + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}
