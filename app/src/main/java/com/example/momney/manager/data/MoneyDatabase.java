package com.example.momney.manager.data;

import java.util.List;

public interface MoneyDatabase {

    List<MoneyEntry> getAllTransactions();

    void insert(MoneyEntry money);

    void update(MoneyEntry money);

    void delete(MoneyEntry money);
}
