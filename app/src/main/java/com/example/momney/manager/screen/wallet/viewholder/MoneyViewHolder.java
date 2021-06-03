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
import com.example.momney.manager.data.MoneyEntry;
import com.example.momney.manager.screen.wallet.data.DateHeader;
import com.example.momney.manager.screen.wallet.data.MoneyData;
import com.example.momney.manager.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MoneyViewHolder extends RecyclerView.ViewHolder {
    private final TextView numDate;
    private final TextView textDate;
    private final TextView content;
    private final TextView description;
    private final TextView amountSpent;
    private final ImageView transIcon;

    private MoneyData moneyData;

    public MoneyViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        numDate = itemView.findViewById(R.id.num_date);
        textDate = itemView.findViewById(R.id.text_date);
        content = itemView.findViewById(R.id.content);
        description = itemView.findViewById(R.id.description);
        amountSpent = itemView.findViewById(R.id.amount_spend);
        transIcon = itemView.findViewById(R.id.trans_icon);
    }

    public MoneyData getMoneyData() {
        return moneyData;
    }

    public void build(MoneyData moneyData) {
        this.moneyData = moneyData;

        MoneyEntry entry = moneyData.getMoneyEntry();

        Calendar calendar = Calendar.getInstance();
        long date = entry.getTime();
        calendar.setTimeInMillis(date);
        numDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        textDate.setText(Utils.millisecondToDateInWeek(entry.getTime()));
        content.setText(entry.getContent());
        description.setText(entry.getDescription());
        int amount = moneyData.getMoneyEntry().getAmount();
        if (amount > 0) {
            amountSpent.setText(String.format("+%s", Utils.amountToString(amount, "VND")));
            amountSpent.setTextColor(itemView.getContext().getResources().getColor(R.color.income));
        } else {
            amountSpent.setText(String.format("%s", Utils.amountToString(amount, "VND")));
            amountSpent.setTextColor(itemView.getContext().getResources().getColor(R.color.expense));
        }

        transIcon.setImageResource(Utils.contentToIcon(moneyData.getMoneyEntry().getContent()));

    }

    public static MoneyViewHolder create(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_item, parent, false);
        return new MoneyViewHolder(v);
    }

}
