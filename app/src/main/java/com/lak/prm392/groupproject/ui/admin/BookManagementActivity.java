package com.lak.prm392.groupproject.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.adapter.BookAdminAdapter;
import com.lak.prm392.groupproject.data.local.entities.Book;
import com.lak.prm392.groupproject.model.AdminViewModel;

public class BookManagementActivity extends AppCompatActivity {
    private AdminViewModel viewModel;
    private BookAdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_management);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        RecyclerView rv = findViewById(R.id.rvBooks);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdminAdapter(new BookAdminAdapter.OnBookActionListener() {
            @Override
            public void onEdit(Book book) {
                showBookDialog(book);
            }

            @Override
            public void onDelete(Book book) {
                viewModel.deleteBook(book);
                Toast.makeText(BookManagementActivity.this, "Đã xoá sách", Toast.LENGTH_SHORT).show();
            }
        });

        rv.setAdapter(adapter);
        viewModel.getAllBooks().observe(this, adapter::setBookList);

        findViewById(R.id.btnAddBook).setOnClickListener(v -> showBookDialog(null));
    }

    private void showBookDialog(@Nullable Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_book, null);
        builder.setView(dialogView);

        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtAuthor = dialogView.findViewById(R.id.edtAuthor);
        EditText edtQuantity = dialogView.findViewById(R.id.edtQuantity);

        if (book != null) {
            edtTitle.setText(book.title);
            edtAuthor.setText(book.author);
            edtQuantity.setText(String.valueOf(book.quantity));
        }

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String title = edtTitle.getText().toString();
            String author = edtAuthor.getText().toString();
            int quantity = Integer.parseInt(edtQuantity.getText().toString());

            if (book == null) {
                viewModel.insertBook(new Book(title, author, quantity));
            } else {
                book.title = title;
                book.author = author;
                book.quantity = quantity;
                viewModel.updateBook(book);
            }
        });

        builder.setNegativeButton("Huỷ", null);
        builder.show();
    }
}

