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
}

