package com.example.momney.manager.screen.wallet.data;

public class TotalHeader implements TransactionData {
    private long income;
    private long expense;

    public TotalHeader(long income, long expense) {
        this.income = income;
        this.expense = expense;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public long getExpense() {
        return expense;
    }

    public void setExpense(long expense) {
        this.expense = expense;
    }
}
