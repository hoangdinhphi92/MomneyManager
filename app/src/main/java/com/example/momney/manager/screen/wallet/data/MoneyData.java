package com.example.momney.manager.screen.wallet.data;

import com.example.momney.manager.data.MoneyEntry;

public class MoneyData implements TransactionData {
    private MoneyEntry moneyEntry;

    public MoneyEntry getMoneyEntry() {
        return moneyEntry;
    }

    public void setMoneyEntry(MoneyEntry moneyEntry) {
        this.moneyEntry = moneyEntry;
    }

    public MoneyData(MoneyEntry moneyEntry) {
        this.moneyEntry = moneyEntry;
    }
}
