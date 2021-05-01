package com.example.momney.manager.data;

public interface MoneyDatabase {

    void insert(MoneyEntry money);

    void update(MoneyEntry money);

    void delete(MoneyEntry money);
}
