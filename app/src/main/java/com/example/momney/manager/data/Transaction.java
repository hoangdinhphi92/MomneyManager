package com.example.momney.manager.data;

import com.example.momney.manager.R;

public class Transaction {
    String content;
    String description;
    int amount;
    private final int imageResource;

    public Transaction(String content, String description, int amount, int imageResource) {
        this.content = content;
        this.description = description;
        this.amount = amount;
        this.imageResource = imageResource;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResource() {
        return imageResource;
    }
}
