package com.example.toshokan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.toshokan.model.Book;
import com.example.toshokan.model.BorrowLog;
import com.example.toshokan.session.SessionManager;
import com.example.toshokan.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ToshokanDB";
    private static final int DB_VERSION = 1;
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Book (id INTEGER PRIMARY KEY, title TEXT, author TEXT, quantity INTEGER, imageResId INTEGER, description TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS BorrowLog (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, bookId INTEGER, borrowDate TEXT, dueDate TEXT, returnDate TEXT, status TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Fine (id INTEGER PRIMARY KEY AUTOINCREMENT, borrowId INTEGER, amount INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Book");
        db.execSQL("DROP TABLE IF EXISTS BorrowLog");
        db.execSQL("DROP TABLE IF EXISTS Fine");
        onCreate(db);
    }

    // ✅ Mượn sách: giảm số lượng, lưu log mượn
    public static void borrowBook(Context context, int bookId) {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        int userId = new SessionManager(context).getUserId();
        String borrowDate = DateUtils.getToday();
        String returnDate = DateUtils.getDateAfterDays(7);

        db.execSQL("UPDATE Book SET quantity = quantity - 1 WHERE id = ?", new Object[]{bookId});
        db.execSQL("INSERT INTO BorrowLog(userId, bookId, borrowDate, dueDate, status) VALUES (?, ?, ?, ?, ?)",
                new Object[]{userId, bookId, borrowDate, returnDate, "borrowed"});
    }

    // ✅ Kiểm tra đã mượn sách này chưa (chưa trả)
    public boolean hasBorrowedNotReturned(int bookId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BorrowLog WHERE bookId = ? AND userId = ? AND status = ?",
                new String[]{String.valueOf(bookId), String.valueOf(userId), "borrowed"});
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }

    // ✅ Thêm bản ghi mượn sách
    public void insertBorrowLog(int bookId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bookId", bookId);
        values.put("userId", userId);
        values.put("borrowDate", getCurrentDate());
        values.put("dueDate", DateUtils.getDateAfterDays(7));
        values.put("status", "borrowed");
        db.insert("BorrowLog", null, values);
    }

    // ✅ Cập nhật số lượng sách
    public void updateBookQuantity(int bookId, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);
        db.update("Book", values, "id = ?", new String[]{String.valueOf(bookId)});
    }

    // ✅ Trả sách: tăng số lượng, cập nhật log, ghi phạt nếu trễ
    public void returnBook(int borrowLogId, int bookId) {
        SQLiteDatabase db = getWritableDatabase();

        // Update BorrowLog (return_date, status)
        ContentValues values = new ContentValues();
        values.put("returnDate", DateUtils.getToday()); // Đã sửa lại đúng
        values.put("status", "returned");
        db.update("BorrowLog", values, "id = ?", new String[]{String.valueOf(borrowLogId)});

        // Tăng số lượng sách
        db.execSQL("UPDATE Book SET quantity = quantity + 1 WHERE id = ?", new Object[]{bookId});
    }

    // ✅ Lấy ngày hiện tại
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    // ✅ Truy vấn toàn bộ sách
    public static ArrayList<Book> getAllBooks(Context context) {
        SQLiteDatabase db = getInstance(context).getReadableDatabase();
        ArrayList<Book> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Book", null);
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            book.title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            book.author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
            book.quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            book.imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"));
            book.description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            list.add(book);
        }
        cursor.close();
        return list;
    }

    // ✅ Lấy 1 cuốn sách theo ID
    public Book getBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Book book = new Book();
            book.id = cursor.getInt(0);
            book.title = cursor.getString(1);
            book.author = cursor.getString(2);
            book.quantity = cursor.getInt(3);
            book.imageResId = cursor.getInt(4);
            book.description = cursor.getString(5);
            cursor.close();
            return book;
        }
        cursor.close();
        return null;
    }

    // ✅ Lấy danh sách mượn theo user
    public static ArrayList<BorrowLog> getBorrowLogsByUser(Context context, int userId) {
        SQLiteDatabase db = getInstance(context).getReadableDatabase();
        ArrayList<BorrowLog> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM BorrowLog WHERE userId = ?", new String[]{String.valueOf(userId)});
        while (cursor.moveToNext()) {
            BorrowLog log = new BorrowLog();
            log.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            log.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("userId")));
            log.setBookId(cursor.getInt(cursor.getColumnIndexOrThrow("bookId")));
            log.setBorrowDate(cursor.getString(cursor.getColumnIndexOrThrow("borrowDate")));
            log.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow("dueDate")));
            log.setReturnDate(cursor.getString(cursor.getColumnIndexOrThrow("returnDate")));
            log.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
            list.add(log);
        }
        cursor.close();
        return list;
    }
}
