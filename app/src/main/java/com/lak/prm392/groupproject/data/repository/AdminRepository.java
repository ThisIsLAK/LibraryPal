package com.lak.prm392.groupproject.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lak.prm392.groupproject.data.local.dao.BookDao;
import com.lak.prm392.groupproject.data.local.dao.TopBook;
import com.lak.prm392.groupproject.data.local.dao.UserDao;
import com.lak.prm392.groupproject.data.local.dao.ViolationLogDao;
import com.lak.prm392.groupproject.data.local.database.AppDatabase;
import com.lak.prm392.groupproject.data.local.entities.Book;
import com.lak.prm392.groupproject.data.local.entities.StatEntry;
import com.lak.prm392.groupproject.data.local.entities.User;
import com.lak.prm392.groupproject.data.local.entities.ViolationLog;
import com.lak.prm392.groupproject.utils.AppExecutors;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdminRepository {
    private final UserDao userDao;
    private final BookDao bookDao;
    private final ViolationLogDao violationLogDao;
    private final Executor executor;

    public AdminRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        userDao = db.userDao();
        bookDao = db.bookDao();
        violationLogDao = db.violationLogDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // USER
    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public LiveData<List<User>> getBannedUsers() {
        return userDao.getBannedUsers();
    }

    public void banUser(int userId, String until) {
        executor.execute(() -> userDao.banUser(userId, until));
    }

    public void unbanUser(int userId) {
        executor.execute(() -> userDao.unbanUser(userId));
    }

    public void incrementViolation(int userId) {
        executor.execute(() -> userDao.incrementViolation(userId));
    }

    public void insertViolationLog(ViolationLog log) {
        executor.execute(() -> violationLogDao.insert(log));
    }

    public LiveData<List<ViolationLog>> getViolationsByUser(int userId) {
        return violationLogDao.getViolationsByUser(userId);
    }

    public void insertUser(User user) {
        AppExecutors.getInstance().diskIO().execute(() -> userDao.insertUser(user));
    }
    public LiveData<List<ViolationLog>> getAllViolations() {
        return violationLogDao.getAllViolations();
    }

    public LiveData<Map<String, Integer>> getViolationStats(String range) {
        MutableLiveData<Map<String, Integer>> liveData = new MutableLiveData<>();

        AppExecutors.getInstance().diskIO().execute(() -> {
            List<StatEntry> stats = violationLogDao.getStatsByRange(range);
            Map<String, Integer> map = new LinkedHashMap<>();
            for (StatEntry entry : stats) {
                map.put(entry.label, entry.count);
            }
            liveData.postValue(map);
        });

        return liveData;
    }


//    public LiveData<Map<String, Integer>> getBorrowStats(String range) {
//        MutableLiveData<Map<String, Integer>> liveData = new MutableLiveData<>();
//
//        AppExecutors.getInstance().diskIO().execute(() -> {
//            Map<String, Integer> result = borrowDao.getBorrowStatsByRange(range);
//            liveData.postValue(result);
//        });
//
//        return liveData;
//    }

    // BOOK
    public LiveData<List<Book>> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public void insertBook(Book book) {
        executor.execute(() -> bookDao.insert(book));
    }

    public void updateBook(Book book) {
        executor.execute(() -> bookDao.update(book));
    }

    public void deleteBook(Book book) {
        executor.execute(() -> bookDao.delete(book));
    }

    public LiveData<List<TopBook>> getTopBooks() {
        return bookDao.getTopBorrowedBooks();
    }
}
