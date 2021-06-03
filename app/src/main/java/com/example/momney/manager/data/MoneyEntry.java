package com.example.momney.manager.data;

public class MoneyEntry {
    private int id;
    private int amount; // vnd
    private long time; // ms
    private String content;
    private String description;

    public MoneyEntry(int id, int amount, long time,String content, String description) {
        this.id = id;
        this.amount = amount;
        this.content = content;
        this.time = time;
        this.description = description;
    }

    public MoneyEntry(int amount, long time, String content, String description) {
        this.amount = amount;
        this.time = time;
        this.content = content;
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

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
