package com.lak.prm392.groupproject.data.local.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.lak.prm392.groupproject.data.local.entities.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);
}