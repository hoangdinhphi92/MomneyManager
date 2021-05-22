package com.example.momney.manager.screen.wallet;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momney.manager.screen.wallet.data.DateHeader;
import com.example.momney.manager.screen.wallet.data.MoneyData;
import com.example.momney.manager.screen.wallet.data.TotalHeader;
import com.example.momney.manager.screen.wallet.data.TransactionData;
import com.example.momney.manager.screen.wallet.viewholder.TotalHeaderViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;



public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int TOTAL_HEADER_TYPE = 1;
    public final static int DATE_HEADER_TYPE = 2;
    public final static int MONEY_ITEM_TYPE = 3;

    private final ArrayList<TransactionData> items = new ArrayList<>();

    public void submitList(List<TransactionData> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        TransactionData data = items.get(position);
        if (data instanceof TotalHeader)
            return TOTAL_HEADER_TYPE;
        else if (data instanceof DateHeader)
            return DATE_HEADER_TYPE;
        else if (data instanceof MoneyData)
            return MONEY_ITEM_TYPE;

        return super.getItemViewType(position);
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == TOTAL_HEADER_TYPE) {
            return TotalHeaderViewHolder.create(parent);
        } else  if (viewType == DATE_HEADER_TYPE) {
            //return view holder for date header
        } else  if (viewType == MONEY_ITEM_TYPE) {
            //return view holder for money item
        }

        throw new IllegalArgumentException("Not support viewtype = " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
