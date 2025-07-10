package com.lak.prm392.groupproject.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ViolationLog")
public class ViolationLog {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;

    @NonNull
    public String reason;

    @NonNull
    public String date; // ISO format yyyy-MM-dd

    public String actionTaken;
}

