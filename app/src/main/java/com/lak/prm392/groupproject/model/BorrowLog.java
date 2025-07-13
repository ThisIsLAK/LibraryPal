package com.lak.prm392.groupproject.model;

public class BorrowLog {
    private int id;
    private int userId;
    private int bookId;
    private String bookTitle;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private String status;

    public BorrowLog() {
    }

    public BorrowLog(int id, int bookId, String bookTitle, String borrowDate, String returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Getter và Setter đầy đủ
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isReturned() {
        return returnDate != null && !returnDate.isEmpty();
    }
}
