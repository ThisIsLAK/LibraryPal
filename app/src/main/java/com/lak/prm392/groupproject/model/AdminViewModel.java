package com.lak.prm392.groupproject.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lak.prm392.groupproject.data.local.dao.TopBook;
import com.lak.prm392.groupproject.data.local.dao.UserDao;
import com.lak.prm392.groupproject.data.local.entities.Book;
import com.lak.prm392.groupproject.data.local.entities.User;
import com.lak.prm392.groupproject.data.local.entities.ViolationLog;
import com.lak.prm392.groupproject.data.repository.AdminRepository;
import com.lak.prm392.groupproject.utils.AppExecutors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminViewModel extends AndroidViewModel {
    private final AdminRepository repository;


    public AdminViewModel(@NonNull Application application) {
        super(application);
        repository = new AdminRepository(application);
    }

    // USER
    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

    public LiveData<List<User>> getBannedUsers() {
        return repository.getBannedUsers();
    }

    public void banUser(int userId, String until) {
        repository.banUser(userId, until);
    }

    public void unbanUser(int userId) {
        repository.unbanUser(userId);
    }

    public void addViolation(int userId, String reason, String actionTaken) {
        repository.incrementViolation(userId);
        ViolationLog log = new ViolationLog();
        log.userId = userId;
        log.reason = reason;
        log.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        log.actionTaken = actionTaken;
        repository.insertViolationLog(log);
    }

    public LiveData<List<ViolationLog>> getViolationsByUser(int userId) {
        return repository.getViolationsByUser(userId);
    }
    public LiveData<Map<String, Integer>> getViolationStats(String range) {
        return repository.getViolationStats(range);
    }

//    public LiveData<Map<String, Integer>> getBorrowStats(String range) {
//        return repository.getBorrowStats(range);
//    }
public LiveData<Map<String, Integer>> getBorrowStats(String range) {
    MutableLiveData<Map<String, Integer>> data = new MutableLiveData<>();
    Map<String, Integer> dummy = new LinkedHashMap<>();

    switch (range) {
        case "day":
            dummy.put("2025-07-07", 5);
            dummy.put("2025-07-08", 8);
            dummy.put("2025-0-09", 12);
            dummy.put("2025-0-9", 15);
            break;
        case "week":
            dummy.put("Tuần 26", 30);
            dummy.put("Tuần 27", 45);
            dummy.put("Tuần 28", 50);
            break;
        case "month":
            dummy.put("06-2025", 90);
            dummy.put("07-2025", 120);
            break;
    }

    data.setValue(dummy);
    return data;
}

    public void insertUser(User user) {
        repository.insertUser(user);
    }
    public void addUser(User user) {
        repository.insertUser(user); // ✅ DONE
    }



    // BOOK
    public LiveData<List<Book>> getAllBooks() {
        return repository.getAllBooks();
    }

    public void insertBook(Book book) {
        repository.insertBook(book);
    }

    public void updateBook(Book book) {
        repository.updateBook(book);
    }

    public void deleteBook(Book book) {
        repository.deleteBook(book);
    }

    public LiveData<List<TopBook>> getTopBooks() {
        return repository.getTopBooks();
    }
    public LiveData<List<ViolationLog>> getAllViolations() {
        return repository.getAllViolations();
    }

}


