package com.example.momney.manager.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.momney.manager.R;
import com.example.momney.manager.data.Content;
import com.example.momney.manager.data.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransHolder> {

    private ArrayList<Transaction> transactions;
    private Context mContext;

    public TransactionAdapter(ArrayList<Transaction> transactions, Context mContext) {
        this.transactions = transactions;
        this.mContext = mContext;
    }

    @Override
    public TransHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TransactionAdapter.TransHolder(LayoutInflater.from(mContext).
                inflate(R.layout.transaction_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TransHolder holder, int position) {
        Transaction currentContent = transactions.get(position);

        holder.bindTo(currentContent);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView mContent;
        private TextView mDecription;
        private TextView mAmount;
        private ImageView mIcon;
        private Transaction transaction;
        private CardView mCardView;
        public TransHolder(@NonNull @NotNull View itemView) {

            super(itemView);
            mContent = itemView.findViewById(R.id.content);
            mDecription = itemView.findViewById(R.id.description);
            mAmount = itemView.findViewById(R.id.amount_spend);
            mIcon = itemView.findViewById(R.id.content_img);
            mCardView = itemView.findViewById(R.id.cardview);
            mCardView.setOnCreateContextMenuListener(this);
        }

        @SuppressLint({"ResourceAsColor"})
        void bindTo(Transaction currentTransaction) {
            // Populate the textviews with data.
            this.transaction = currentTransaction;
            mContent.setText(currentTransaction.getContent());
            mDecription.setText(currentTransaction.getDescription());

            if (currentTransaction.getAmount()>0) {
                mAmount.setText("+" + currentTransaction.getAmount());
                mAmount.setTextColor(R.color.blue);
            }
            else {
                mAmount.setText(String.valueOf(currentTransaction.getAmount()));
                mAmount.setTextColor(R.color.expense);
            }
            Glide.with(mContent).load(currentTransaction.getImageResource()).into(mIcon);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 12, 0, "Edit");
            menu.add(this.getAdapterPosition(), 13, 1, "Delete");

        }
    }
}

