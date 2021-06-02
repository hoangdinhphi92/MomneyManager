package com.example.momney.manager.screen.wallet.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momney.manager.R;
import com.example.momney.manager.screen.wallet.data.DateHeader;
import com.example.momney.manager.utils.Utils;

import org.jetbrains.annotations.NotNull;

public class DateViewHolder extends RecyclerView.ViewHolder {
    private final TextView dateHeader;
    private final TextView total;

    public DateViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        dateHeader = itemView.findViewById(R.id.date_header);
        total = itemView.findViewById(R.id.total);
    }

    public void build(DateHeader dateHeader1){
        dateHeader.setText(Utils.millisecondToString(dateHeader1.getDate(), dateHeader1.getFilter()));
        if(dateHeader1.getTotal()>0){
            total.setText(String.format("+%s",Utils.amountToString((int) dateHeader1.getTotal(), "VND")));
            total.setTextColor(itemView.getContext().getResources().getColor(R.color.income));
        }
        else {
            total.setText(String.format("%s",Utils.amountToString((int) dateHeader1.getTotal(), "VND")));
            total.setTextColor(itemView.getContext().getResources().getColor(R.color.expense));
        }
    }

    public static DateViewHolder create(ViewGroup parent){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_header_item, parent, false);
        return new DateViewHolder(v);
    }

}
