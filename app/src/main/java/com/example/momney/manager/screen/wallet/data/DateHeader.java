package com.example.momney.manager.screen.wallet.data;

public class DateHeader implements TransactionData {
    private long date; // ms

    public DateHeader(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
