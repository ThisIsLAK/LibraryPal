package com.lak.prm392.groupproject.data.repository;

import android.content.Context;

import com.lak.prm392.groupproject.data.local.dao.UserDao;
import com.lak.prm392.groupproject.data.local.database.AppDatabase;
import com.lak.prm392.groupproject.data.local.entities.User;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
    }

    public User login(String email, String password) {
        return userDao.login(email, password);
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public boolean isEmailExists(String email) {
        return userDao.getUserByEmail(email) != null;
    }
}
