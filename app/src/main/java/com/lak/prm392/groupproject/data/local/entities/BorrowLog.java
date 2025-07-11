package com.lak.prm392.groupproject.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "BorrowLog")
public class BorrowLog {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int user_id;

    public int book_id;

    @NonNull
    public String borrow_date;

    @NonNull
    public String due_date;

    public String return_date; // nullable

    @NonNull
    public String status; // e.g. "borrowed", "returned"
}

