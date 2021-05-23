package com.example.momney.manager.utils;

import com.example.momney.manager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

public class Utils {
    public static String millisecondToString(long ms) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(ms);

        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);

        Calendar calendar = Calendar.getInstance();

        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisYear = calendar.get(Calendar.YEAR);

        if (day == today && month == thisMonth && year == thisYear) return "Today";
        else if (day == today - 1 && month == thisMonth && year == thisYear) return "Yesterday";
        else return date + "/" + month + "/" + year;
    }

    public static boolean differentDate(long a, long b){
        Calendar date1 = Calendar.getInstance();
        date1.setTimeInMillis(a);
        Calendar date2 = Calendar.getInstance();
        date1.setTimeInMillis(b);
        return date1.get(Calendar.DAY_OF_YEAR) != date2.get(Calendar.DAY_OF_YEAR);
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
}
