package com.example.momney.manager.data;

public class MoneyEntry {
    private int id;
    private int amount;
    private int time;
    private String description;

    public MoneyEntry(int id, int amount, int time, String description) {
        this.id = id;
        this.amount = amount;
        this.time = time;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
