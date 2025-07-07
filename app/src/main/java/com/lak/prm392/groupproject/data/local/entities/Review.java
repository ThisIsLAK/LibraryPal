package com.lak.prm392.groupproject.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Review {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public int bookId;
    public String comment;
    public int rating; // 1-5
    public String createdAt;
}

