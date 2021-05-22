package com.example.momney.manager.screen.wallet.viewholder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momney.manager.R;
import com.example.momney.manager.screen.wallet.data.DateHeader;
import com.example.momney.manager.screen.wallet.data.MoneyData;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MoneyViewHolder extends RecyclerView.ViewHolder {
    private TextView numDate;
    private TextView textDate;
    private TextView content;
    private TextView description;
    private TextView amountSpent;
    private ImageView transIcon;
    public MoneyViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        numDate = itemView.findViewById(R.id.num_date);
        textDate = itemView.findViewById(R.id.text_date);
        content = itemView.findViewById(R.id.content);
        description = itemView.findViewById(R.id.description);
        amountSpent = itemView.findViewById(R.id.amount_spend);
        transIcon = itemView.findViewById(R.id.trans_icon);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public void build(MoneyData moneyData){
        int date = moneyData.getMoneyEntry().getTime()/1000000;
        numDate.setText(String.valueOf(date));
        textDate.setText(getTextDate(moneyData));
        content.setText(moneyData.getMoneyEntry().getContent());
        description.setText(moneyData.getMoneyEntry().getDescription());
        int amount = moneyData.getMoneyEntry().getAmount();
        if (amount>0) {
            amountSpent.setText( "+" + amount +"VND");
            amountSpent.setTextColor(R.color.income);
        }
        else {
            amountSpent.setText( amount +"VND");
            amountSpent.setTextColor(R.color.expense);
        }

        transIcon.setImageAlpha(getIcon(moneyData.getMoneyEntry().getContent()));

    }

    public static MoneyViewHolder create(ViewGroup parent){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_item, parent, false);
        return new MoneyViewHolder(v);
    }

    public String getTextDate(MoneyData moneyData){
        int date = moneyData.getMoneyEntry().getTime();
        int day = date/1000000;
        int month = (date - day*1000000)/10000;
        int year = (date - day*1000000 - month*10000);

        Calendar newDate = new GregorianCalendar();
        newDate.set(year,month,date);
        Calendar calendar = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.roll(Calendar.DATE, -1);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("E");
        String formatedDate = newFormat.format(newDate.getTime());

        @SuppressLint ("SimpleDateFormat") SimpleDateFormat format;
        if(newDate.getTime().equals(calendar.getTime())) { return "Today";}
        else  if(newDate.getTime().equals(yesterday.getTime())) { return "Yesterday";}
        else { return formatedDate; }
    }

    private Integer getIcon(String content) {
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
