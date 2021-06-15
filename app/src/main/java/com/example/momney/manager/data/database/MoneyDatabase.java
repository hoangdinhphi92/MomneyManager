package com.example.momney.manager.data.database;

import com.example.momney.manager.data.MoneyEntry;

import java.util.List;

public interface MoneyDatabase {

    List<MoneyEntry> getTransactions();

    List<MoneyEntry> getTransactions(long startTime, long endTime);

    List<MoneyEntry> getIncomes();

    List<MoneyEntry> getIncomes(long startTime, long endTime);

    List<MoneyEntry> getExpense();

    List<MoneyEntry> getExpense(long startTime, long endTime);

    long total();

    long total(long startTime, long endTime);

    long totalIncome();

    long totalIncome(long startTime, long endTime);

    long totalExpense();

    long totalExpense(long startTime, long endTime);

    void insert(MoneyEntry money);

    void update(MoneyEntry money);

    void delete(MoneyEntry money);
}
