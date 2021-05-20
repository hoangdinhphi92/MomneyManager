package com.example.momney.manager.screen;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateHolder> {
    @NonNull
    @NotNull
    @Override
    public DateHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DateHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DateHolder extends RecyclerView.ViewHolder {
        public DateHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
