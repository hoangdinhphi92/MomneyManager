package com.example.momney.manager.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;

import com.example.momney.manager.R;
import com.example.momney.manager.data.MoneyDatabase;
import com.github.mikephil.charting.data.Entry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.example.momney.manager.R.drawable.*;
import static com.example.momney.manager.screen.wallet.WalletFragment.FILTER_ALL;
import static com.example.momney.manager.screen.wallet.WalletFragment.FILTER_MONTH;
import static com.example.momney.manager.screen.wallet.WalletFragment.FILTER_WEEK;
import static java.util.Calendar.APRIL;
import static java.util.Calendar.AUGUST;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.JULY;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.NOVEMBER;
import static java.util.Calendar.OCTOBER;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SEPTEMBER;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

public class Utils {

    public static Locale locale;

    public static final String[] LANGUAGE = {"en","vi","zh","ja"};
    public static String curLanguage = Locale.getDefault().getLanguage();
    public static final String[] currency = {"USD","VND","CNY","JPY"};
    public static String mCurrency = currency[1];

    public static final String LANGUAGE_KEY = "language";
    // Key for current color
    public static final String CURRENCY_KEY = "currency";

    public static SharedPreferences mPreferences;
    public static String sharedPrefFile =
            "com.example.momney.manager";

    public static int getDayOfMonth(long ms){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        return date.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfYear(long ms){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        return date.get(Calendar.DAY_OF_YEAR);
    }
    public static int getMonth(long ms){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        return date.get(Calendar.MONTH);
    }
    public static int getYear(long ms){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        return date.get(Calendar.YEAR);
    }
    public static int getWeek(long ms){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        return date.get(Calendar.WEEK_OF_YEAR);
    }
    public static String millisecondToString(long ms, int filter, Context context) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int week = date.get(Calendar.WEEK_OF_YEAR);
        int year = date.get(Calendar.YEAR);
        int dayOY = date.get(Calendar.DAY_OF_YEAR);

        Calendar calendar = Calendar.getInstance();

        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int thisWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisYear = calendar.get(Calendar.YEAR);
        int thisDayOY = calendar.get(Calendar.DAY_OF_YEAR);

        switch (filter){
            case FILTER_ALL:
                if (dayOY == thisDayOY && year ==thisYear) return context.getString(R.string.today);
                else if (dayOY == thisDayOY - 1 && year ==thisYear) return context.getString(R.string.yesterday);
                else {
                    return day + "/" + (month+1) + "/" + year;
                }
            case FILTER_MONTH:
                if (month == thisMonth && year ==thisYear) return context.getString(R.string.this_month);
                else {
                    return monthToString(context, month)+ " " + year;
                }
            case FILTER_WEEK:
                if (week == thisWeek && year == thisYear) return context.getString(R.string.this_week);
                else {
                    return getStringWeek(ms);
                }
            default:
                return "";
        }
    }

    public static boolean differentTime(long a, long b, int filter) {
        Calendar date1 = Calendar.getInstance();
        date1.setTimeInMillis(a);
        Calendar date2 = Calendar.getInstance();
        date2.setTimeInMillis(b);
        switch (filter){
            case FILTER_ALL:
                return date1.get(Calendar.DAY_OF_YEAR) != date2.get(Calendar.DAY_OF_YEAR);
            case FILTER_MONTH:
                return date1.get(Calendar.MONTH) != date2.get(Calendar.MONTH);
            case FILTER_WEEK:
                return date1.get(Calendar.WEEK_OF_YEAR) != date2.get(Calendar.WEEK_OF_YEAR);
            default:
                return false;
        }

    }

    public static String millisecondToDateInWeek(long ms, Context context) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);

        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();

        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisYear = calendar.get(Calendar.YEAR);

        if (day == today && month == thisMonth && year == thisYear) return context.getString(R.string.today);
        else if (day == today - 1 && month == thisMonth && year == thisYear) return context.getString(R.string.yesterday);
        else
            switch (date.get(Calendar.DAY_OF_WEEK)) {
                case MONDAY:
                    return context.getString(R.string.monday);
                case TUESDAY:
                    return context.getString(R.string.tuesday);
                case WEDNESDAY:
                    return context.getString(R.string.wednesday);
                case THURSDAY:
                    return context.getString(R.string.thursday);
                case FRIDAY:
                    return context.getString(R.string.friday);
                case SATURDAY:
                    return context.getString(R.string.saturday);
                case SUNDAY:
                    return context.getString(R.string.sunday);
                default:
                    return "";
            }

    }

    public static String monthToString(Context context, int month){
        switch (month){
            case JANUARY:
                return context.getString(R.string.january);
            case FEBRUARY:
                return context.getString(R.string.february);
            case MARCH:
                return context.getString(R.string.march);
            case APRIL:
                return context.getString(R.string.april);
            case MAY:
                return context.getString(R.string.may);
            case JUNE:
                return context.getString(R.string.june);
            case JULY:
                return context.getString(R.string.july);
            case AUGUST:
                return context.getString(R.string.august);
            case SEPTEMBER:
                return context.getString(R.string.september);
            case OCTOBER:
                return context.getString(R.string.october);
            case NOVEMBER:
                return context.getString(R.string.november);
            case DECEMBER:
                return context.getString(R.string.december);
            default:
                return "";
        }
    }

    public static int intToIcon(int a) {
        switch (a) {
            case 1:
                return (int) img_eating;
            case 2:
                return (int) img_travel;
            case 3:
                return (int) img_sport;
            case 4:
                return (int) img_house;
            case 5:
                return (int) img_bill;
            case 6:
                return (int) img_health;
            case 7:
                return (int) img_education;
            case 8:
                return (int) img_game;
            case 9:
                return (int) img_services;
            case 10:
                return (int) img_salary;
            case 11:
                return (int) img_houseware;
            case 12:
                return (int) img_transfer;
            case 13:
                return (int) img_pet;
            case 14:
                return (int) img_vehicle;
            case 15:
                return (int) img_other;
            default:
                return 0;
        }
    }

    public static String getContentFromId(int id) {
      //  switch (id) {
//            case 1:
//                return context.getString(R.string.eating);
//            case 2:
//                return context.getString(R.string.travel);
//            case 3:
//                return context.getString(R.string.sport);
//            case 4:
//                return context.getString(R.string.house_rent);
//            case 5:
//                return context.getString(R.string.bill);
//            case 6:
//                return context.getString(R.string.health);
//            case 7:
//                return context.getString(R.string.education);
//            case 8:
//                return context.getString(R.string.game);
//            case 9:
//                return context.getString(R.string.online_services);
//            case 10:
//                return context.getString(R.string.salary);
//            case 11:
//                return context.getString(R.string.housewares);
//            case 12:
//                return context.getString(R.string.transfers);
//            case 13:
//                return context.getString(R.string.pet);
//            case 14:
//                return context.getString(R.string.vihicle);
//            default:
//                return context.getString(R.string.orthers);
  //      }
        return "";
    }

    public static String getContentFromInt(Context context, int id) {
        switch (id) {
            case 1:
                return context.getString(R.string.eating);
            case 2:
                return context.getString(R.string.travel);
            case 3:
                return context.getString(R.string.sport);
            case 4:
                return context.getString(R.string.house_rent);
            case 5:
                return context.getString(R.string.bill);
            case 6:
                return context.getString(R.string.health);
            case 7:
                return context.getString(R.string.education);
            case 8:
                return context.getString(R.string.game);
            case 9:
                return context.getString(R.string.online_services);
            case 10:
                return context.getString(R.string.salary);
            case 11:
                return context.getString(R.string.housewares);
            case 12:
                return context.getString(R.string.transfers);
            case 13:
                return context.getString(R.string.pet);
            case 14:
                return context.getString(R.string.vihicle);
            default:
                return context.getString(R.string.orthers);
        }
    }

    public static int iconToInt(int icon) {
        switch (icon) {
            case (int) img_eating:
                return 1;
            case (int) img_travel:
                return 2;
            case (int) img_sport:
                return 3;
            case (int) img_house:
                return 4;
            case (int) img_bill:
                return 5;
            case (int) img_health:
                return 6;
            case (int) img_education:
                return 7;
            case (int) img_game:
                return 8;
            case (int) img_services:
                return 9;
            case (int) img_salary:
                return 10;
            case (int) img_houseware:
                return 11;
            case (int) img_transfer:
                return 12;
            case (int) img_pet:
                return 13;
            case (int) img_vehicle:
                return 14;
            case (int) img_other:
                return 15;
            default:
                return 0;
        }
    }

    public static String amountToString(int amount, String money) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance(money));
        return format.format(amount);
    }

    public static String getStringWeek(long ms){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        int day = date.get(Calendar.DAY_OF_YEAR);
        int year = date.get(Calendar.YEAR);
        int dayOW = date.get(Calendar.DAY_OF_WEEK);
        Calendar firstDay = Calendar.getInstance() , lastDay = Calendar.getInstance();
        if (dayOW!=1) {
            if(day-dayOW+2 < 0){
                firstDay.set(Calendar.YEAR, year-1);
                firstDay.set(Calendar.DAY_OF_YEAR, dayInYear(year-1) + day-dayOW+2);

                lastDay.set(Calendar.YEAR, year);
                lastDay.set(Calendar.DAY_OF_YEAR, day-dayOW + 8 );
            }
            else if (day-dayOW+8 > dayInYear(year)){
                firstDay.set(Calendar.YEAR, year);
                firstDay.set(Calendar.DAY_OF_YEAR, day - dayOW + 2);

                lastDay.set(Calendar.YEAR, year+1);
                lastDay.set(Calendar.DAY_OF_YEAR, day-dayOW + 8 - dayInYear(year));
            }
            else {
                firstDay.set(Calendar.YEAR, year);
                firstDay.set(Calendar.DAY_OF_YEAR, day - dayOW + 2);

                lastDay.set(Calendar.YEAR, year);
                lastDay.set(Calendar.DAY_OF_YEAR, day-dayOW+ 8 );
            }
        }
        else {
            if (day < 7) {
                firstDay.set(Calendar.YEAR, year - 1);
                firstDay.set(Calendar.DAY_OF_YEAR, dayInYear(year - 1) + day - 6);

            }
            else {
                firstDay.set(Calendar.YEAR, year );
                firstDay.set(Calendar.DAY_OF_YEAR, day - 6);

            }
            lastDay.set(Calendar.YEAR, year);
            lastDay.set(Calendar.DAY_OF_YEAR, day);
        }
        SimpleDateFormat format;
        format = new SimpleDateFormat("dd/MM", Locale.US);
        long fDay = firstDay.getTimeInMillis();
        long lDay = lastDay.getTimeInMillis();
        return format.format(fDay) +"-"+ format.format(lDay);

    }

    public static int dayInYear(int year){
        if(year%4==0){
            if (year%100==0){
                if(year%400==0) return 366;
                else return 365;
            }
            else return 366;
        }
        else return 365;
    }

    public static int ratio(){
        int ratio;
        switch (mCurrency){
            case "USD":
                ratio = 23045;
                break;
            case "CNY":
                ratio = 3603;
                break;
            case "JPY":
                ratio = 210;
                break;
            default: ratio=1;
        }
        return ratio;
    }

    public static String amountToString(int amount){
        int ratio;
        switch (mCurrency){
            case "USD":
                ratio = 23045;
                break;
            case "CNY":
                ratio = 3603;
                break;
            case "JPY":
                ratio = 210;
                break;
            default: ratio=1;
        }

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance(mCurrency));
        return String.format("%s", format.format((float) amount/ratio));
    }

    public static void setLocale(String localeName, Context context) {
            locale = new Locale(localeName);
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static void restoreState(Context context) {
        mPreferences = context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        curLanguage = mPreferences.getString(LANGUAGE_KEY, "vi");
        setLocale(curLanguage, context);
        mCurrency = mPreferences.getString(CURRENCY_KEY, "VND") ;
    }

    public static long getYearInMillis(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 0, 1);
        return calendar.getTimeInMillis();
    }

    public static long getMonthInMillis(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.getTimeInMillis();
    }

    public static ArrayList<Long> wheelList(long ms){
        ArrayList<Long> list = new ArrayList<>();
        for (int i=0; i<=19; i++){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ms);
            calendar.add(Calendar.DATE, -7*i);
            long temp = calendar.getTimeInMillis();
            list.add(temp);
        }
        return list;
    }

    public static ArrayList<String> getXAxis(int filter, long date, Context context){
        ArrayList<String> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int month = calendar.get(Calendar.MONTH);
        YearMonth yearMonthObject = YearMonth.of(calendar.get(Calendar.YEAR), month+1 );
        int daysInMonth = yearMonthObject.lengthOfMonth();
        switch (filter){
            case FILTER_ALL:
                list.add("Jan");
                list.add("Feb");
                list.add("Mar");
                list.add("Apr");
                list.add("May");
                list.add("Jun");
                list.add("Jul");
                list.add("Aug");
                list.add("Sep");
                list.add("Oct");
                list.add("Nov");
                list.add("Dec");
                break;
            case FILTER_MONTH:
                for (int i=1; i<=daysInMonth; i++){
                    list.add(i+"/"+ (month+1));
                }
                break;
            case FILTER_WEEK:
                list.add("Mon");
                list.add("Tue");
                list.add("Wed");
                list.add("Thu");
                list.add("Fri");
                list.add("Sat");
                list.add("Sun");
               break;
        }
        return list;
    }
    public static ArrayList<Entry> incomeEntry(int filter, long date, MoneyDatabase database){
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Integer> incomeList = database.getIncomeEntry(filter, date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        YearMonth yearMonthObject = YearMonth.of(year, month+1 );
        int daysInMonth = yearMonthObject.lengthOfMonth();
        switch (filter){
            case FILTER_ALL:
                for (int i=0; i<12; i++){
                    entries.add(new Entry(i, (float) incomeList.get(i)/ratio()));
                }
                break;
            case FILTER_MONTH:
                for (int i=0; i<daysInMonth; i++){
                    entries.add(new Entry(i, (float) incomeList.get(i)/ratio()));
                }
                break;
            case FILTER_WEEK:
                for (int i=0; i < 7; i++){
                    entries.add(new Entry(i, (float) incomeList.get(i)/ratio()));
                }
                break;
        }
        return entries;
    }

    public static ArrayList<Entry> expenseEntry(int filter, long date, MoneyDatabase database){
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Integer> incomeList = database.getExpenseEntry(filter, date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        YearMonth yearMonthObject = YearMonth.of(year, month+1 );
        int daysInMonth = yearMonthObject.lengthOfMonth();
        switch (filter){
            case FILTER_ALL:
                for (int i=0; i<12; i++){
                    entries.add(new Entry(i, (float) incomeList.get(i)/ratio()));
                }
                break;
            case FILTER_MONTH:
                for (int i=0; i<daysInMonth; i++){
                    entries.add(new Entry(i, (float) incomeList.get(i)/ratio()));
                }
                break;
            case FILTER_WEEK:
                for (int i=0; i < 7; i++){
                    entries.add(new Entry(i, (float) incomeList.get(i)/ratio()));
                }
                break;
        }
        return entries;
    }

    public static long firstDayOfWeek(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int dayOW = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOW==1) calendar.add(Calendar.DATE, -6);
        else calendar.add(Calendar.DATE, 1 - dayOW);
        return calendar.getTimeInMillis();

    }
}
