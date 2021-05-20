package com.example.momney.manager.data;

public class Content {
    private String content;
    private final int imageResource;

    public Content(String content, int imageResource) {
        this.content = content;
        this.imageResource = imageResource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageResource() {
        return imageResource;
    }
}
