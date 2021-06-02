package com.example.momney.manager.screen.wallet.data;

public class DateHeader implements TransactionData {
    private long date; // ms
    private int total;
    private int filter;

    public DateHeader(long date, int total, int filter) {
        this.date = date;
        this.total = total;
        this.filter = filter;
    }

    public long getDate() {
        return date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }
}
