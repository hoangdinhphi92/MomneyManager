package com.example.momney.manager.screen.wallet.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momney.manager.R;
import com.example.momney.manager.screen.wallet.data.DateHeader;

import org.jetbrains.annotations.NotNull;

public class DateViewHolder extends RecyclerView.ViewHolder {
    private TextView dateHeader;

    public DateViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        dateHeader = itemView.findViewById(R.id.date_header);
    }

    public void build(DateHeader dateHeader1){
        dateHeader.setText(String.valueOf(dateHeader1.getDate()));
    }

    public static DateViewHolder create(ViewGroup parent){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_header_item, parent, false);
        return new DateViewHolder(v);
    }

}
