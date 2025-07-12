
package com.example.toshokan.model;

public class Book {
    public int id;
    public String title;
    public String author;
    public int quantity;
    public int imageResId;
    public String description;
    public int available; // Thêm trường available

    public Book() {
    }

    public Book(int id, String title, String author, int quantity, int imageResId, String description, int available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.imageResId = imageResId;
        this.description = description;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
