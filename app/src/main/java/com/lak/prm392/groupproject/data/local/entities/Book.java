package com.lak.prm392.groupproject.data.local.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Book")
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    @NonNull
    public String author;

    public int quantity;
    @Nullable
    @ColumnInfo(name = "imageUri")
    public String imageUri;

    public Book() {} // <-- cần thiết cho Room

    public Book(@NonNull String title, @NonNull String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }
}
