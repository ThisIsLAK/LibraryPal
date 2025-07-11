package com.lak.prm392.groupproject.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lak.prm392.groupproject.data.local.entities.Book;
import com.lak.prm392.groupproject.data.local.entities.BorrowLog;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM Book")
    LiveData<List<Book>> getAllBooks();

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT Book.title, COUNT(*) as borrowCount FROM BorrowLog " +
            "JOIN Book ON BorrowLog.book_id = Book.id " +
            "GROUP BY BorrowLog.book_id ORDER BY borrowCount DESC LIMIT 5")
    LiveData<List<TopBook>> getTopBorrowedBooks();

}

