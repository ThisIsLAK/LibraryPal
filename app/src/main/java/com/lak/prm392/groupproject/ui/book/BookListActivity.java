package com.lak.prm392.groupproject.ui.book;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toshokan.adapter.BookAdapter;

public class BookListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter adapter;

    private final ActivityResultLauncher<Intent> bookDetailLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    adapter.notifyDataSetChanged(); // Làm mới khi quay lại từ BookDetail
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        recyclerView = findViewById(R.id.recyclerView_1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new BookAdapter(this, BookManager.bookList);
        recyclerView.setAdapter(adapter);

        // Đăng ký listener để cập nhật khi sách thay đổi
        BookManager.setOnBookChangeListener(bookId -> adapter.notifyDataSetChanged());
    }
}