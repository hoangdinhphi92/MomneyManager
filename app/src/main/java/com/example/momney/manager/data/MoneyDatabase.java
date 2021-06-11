package com.example.momney.manager.data;

import java.util.ArrayList;
import java.util.List;

public interface MoneyDatabase {

    List<MoneyEntry> getAllTransactions();

    int getAllIncome();

    int getAllExpense();

    ArrayList<Integer> getIncomeEntry(int filter, long date);
    ArrayList<Integer> getExpenseEntry(int filter, long date);

    int total(long date, int filter);

    int totalUse(long date, int filter);

    int totalIncome(long date, int filter);

    int totalUseContent(long date, int filter, String content);

    int totalIncomeContent(long date, int filter, String content);

    void deleteTable();

    void insert(MoneyEntry money);

    void update(MoneyEntry money);

    void delete(MoneyEntry money);
}
