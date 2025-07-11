package com.lak.prm392.groupproject.data.local.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lak.prm392.groupproject.data.local.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM User WHERE isBanned = 1")
    LiveData<List<User>> getBannedUsers();

    @Query("UPDATE User SET isBanned = 1, banUntil = :until WHERE id = :userId")
    void banUser(int userId, String until);

    @Query("UPDATE User SET isBanned = 0, banUntil = null WHERE id = :userId")
    void unbanUser(int userId);

    @Query("UPDATE User SET violationCount = violationCount + 1 WHERE id = :userId")
    void incrementViolation(int userId);
}