package com.lak.prm392.groupproject.model;

public class NotificationItem {
    private String title;
    private String message;
    private String date;

    public NotificationItem(String title, String message, String date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
