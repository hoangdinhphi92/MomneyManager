package com.example.momney.manager.data;

import java.util.List;

public interface MoneyDatabase {

    List<MoneyEntry> getAllTransactions();

    int getAllIncome();

    int getAllExpense();

    int total(long date, int filter);

    void deleteTable();

    void insert(MoneyEntry money);

    void update(MoneyEntry money);

    void delete(MoneyEntry money);
}
