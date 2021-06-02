package com.example.momney.manager.utils;

import com.example.momney.manager.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.Locale;

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
    public static int getWeak(long ms){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        return date.get(Calendar.WEEK_OF_YEAR);
    }
    public static String millisecondToString(long ms, int filter) {
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
                if (dayOY == thisDayOY && year ==thisYear) return "Today";
                else if (dayOY == thisDayOY - 1 && year ==thisYear) return "Yesterday";
                else {
                    return day + "/" + (month+1) + "/" + year;
                }
            case FILTER_MONTH:
                if (month == thisMonth && year ==thisYear) return "This month";
                else {
                    return monthToString(month)+ " " + year;
                }
            case FILTER_WEEK:
                if (week == thisWeek && year == thisYear) return "This week";
                else {
                    return getWeek(ms);
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

    public static String millisecondToDateInWeek(long ms) {
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

        if (day == today && month == thisMonth && year == thisYear) return "Today";
        else if (day == today - 1 && month == thisMonth && year == thisYear) return "Yesterday";
        else
            switch (date.get(Calendar.DAY_OF_WEEK)) {
                case MONDAY:
                    return "Monday";
                case TUESDAY:
                    return "Tuesday";
                case WEDNESDAY:
                    return "Wednesday";
                case THURSDAY:
                    return "Thursday";
                case FRIDAY:
                    return "Friday";
                case SATURDAY:
                    return "Saturday";
                case SUNDAY:
                    return "Sunday";
                default:
                    return "";
            }

    }

    public static String monthToString(int month){
        switch (month){
            case JANUARY:
                return "January";
            case FEBRUARY:
                return "February";
            case MARCH:
                return "March";
            case APRIL:
                return "April";
            case MAY:
                return "May";
            case JUNE:
                return "June";
            case JULY:
                return "July";
            case AUGUST:
                return "August";
            case SEPTEMBER:
                return "September";
            case OCTOBER:
                return "October";
            case NOVEMBER:
                return "November";
            case DECEMBER:
                return "December";
            default:
                return "";
        }
    }

    public static int contentToIcon(String content) {
        switch (content) {
            case "Eating":
                return (int) R.drawable.img_eating;
            case "Travel":
                return (int) R.drawable.img_travel;
            case "Sport":
                return (int) R.drawable.img_sport;
            case "House rent":
                return (int) R.drawable.img_house;
            case "Bill":
                return (int) R.drawable.img_bill;
            case "Health":
                return (int) R.drawable.img_health;
            case "Education":
                return (int) R.drawable.img_education;
            case "Game":
                return (int) R.drawable.img_game;
            case "Online Services":
                return (int) R.drawable.img_services;
            case "Salary":
                return (int) R.drawable.img_salary;
            case "Houseware":
                return (int) R.drawable.img_houseware;
            case "Transfers":
                return (int) R.drawable.img_transfer;
            case "Pet":
                return (int) R.drawable.img_pet;
            case "Vehicle":
                return (int) R.drawable.img_vehicle;
            case "Others":
                return (int) R.drawable.img_other;
            default:
                return 0;
        }
    }

    public static String getContentFromId(int id) {
        switch (id) {
            case 1:
                return "Eating";
            case 2:
                return "Travel";
            case 3:
                return "Sport";
            case 4:
                return "House rent";
            case 5:
                return "Bill";
            case 6:
                return "Health";
            case 7:
                return "Education";
            case 8:
                return "Game";
            case 9:
                return "Online Services";
            case 10:
                return "Salary";
            case 11:
                return "Houseware";
            case 12:
                return "Transfers";
            case 13:
                return "Pet";
            case 14:
                return "Vehicle";
            default:
                return "Others";
        }
    }

    public static String amountToString(int amount, String money) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance(money));

        return format.format(amount);
    }

    public static String getWeek(long ms){
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
}
