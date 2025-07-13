package com.lak.prm392.groupproject.ui.book;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toshokan.adapter.BorrowHistoryAdapter;
import com.example.toshokan.model.Book;

public class BorrowHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerViewHistory;
    private BorrowHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_history);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BorrowHistoryAdapter(BookManager.historyList, book -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận trả sách")
                    .setMessage("Bạn có muốn trả sách '" + book.getTitle() + "' không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        BookManager.borrowedBooks.remove(book.getId());
                        BookManager.historyList.remove(book);
                        Book originalBook = BookManager.getBookById(book.getId());
                        if (originalBook != null) {
                            int newQuantity = originalBook.getQuantity() + 1;
                            int newAvailable = originalBook.getAvailable() + 1;
                            originalBook.setQuantity(newQuantity);
                            originalBook.setAvailable(newAvailable);
                        }
                        adapter.notifyDataSetChanged();
                        BookManager.notifyBookChanged(book.getId()); // Thông báo thay đổi
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .show();
        });
        recyclerViewHistory.setAdapter(adapter);
    }
}