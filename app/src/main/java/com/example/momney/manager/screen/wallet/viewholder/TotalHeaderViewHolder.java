package com.example.momney.manager.screen.wallet.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momney.manager.R;
import com.example.momney.manager.screen.wallet.data.TotalHeader;
import com.example.momney.manager.utils.Utils;

public class TotalHeaderViewHolder extends RecyclerView.ViewHolder {
    private final TextView totalIncome;
    private final TextView totalExpense;

    public TotalHeaderViewHolder(@NonNull View itemView) {
        super(itemView);

        totalIncome = itemView.findViewById(R.id.total_income);
        totalExpense = itemView.findViewById(R.id.total_expense);
    }

    public void build(TotalHeader totalHeader) {
        totalIncome.setText(Utils.amountToString((int) totalHeader.getIncome()));
        totalExpense.setText(Utils.amountToString((int) totalHeader.getExpense()));
    }

    public static TotalHeaderViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_header_layout, parent, false);
        return new TotalHeaderViewHolder(view);
    }

}
