package com.lak.prm392.groupproject.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "User")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    @NonNull
    public String email;

    @NonNull
    public String password;

    @NonNull
    public String role; // student, teacher, admin

    public int violationCount = 0;       // Số lần vi phạm
    public boolean isBanned = false;     // Có bị ban không
    public String banUntil = null;       // Thời gian ban đến (nếu có)
}

