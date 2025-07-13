package com.lak.prm392.groupproject.ui.book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toshokan.model.Book;
import com.lak.prm392.groupproject.R;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView imgDetail;
    private TextView tvDetailName, tvDetailAuthor, tvDetailQuantity, tvDetailAvailable, tvDetailDescription;
    private Button btnBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        imgDetail = findViewById(R.id.imgDetail);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailAuthor = findViewById(R.id.tvDetailAuthor);
        tvDetailQuantity = findViewById(R.id.tvDetailQuantity);
        tvDetailAvailable = findViewById(R.id.tvDetailAvailable);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        btnBorrow = findViewById(R.id.btnBorrow);

        Intent intent = getIntent();
        int bookId = intent.getIntExtra("book_id", -1);

        Book book = BookManager.getBookById(bookId);
        if (book != null) {
            tvDetailName.setText(book.getTitle());
            tvDetailAuthor.setText("Author: " + book.getAuthor());
            tvDetailQuantity.setText("Quantity: " + book.getQuantity());
            tvDetailAvailable.setText("Available: " + book.getAvailable());
            tvDetailDescription.setText("Description: " + book.getDescription());
            imgDetail.setImageResource(book.getImageResId());

            final Book finalBook = new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getImageResId(), book.getDescription(), book.getAvailable());
            btnBorrow.setOnClickListener(v -> {
                if (finalBook.getAvailable() > 0) {
                    if (BookManager.borrowedBooks.contains(finalBook.getId())) {
                        Toast.makeText(BookDetailActivity.this, "Phải trả sách " + finalBook.getTitle() + " thì mới được mượn tiếp!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int newQuantity = finalBook.getQuantity() - 1;
                    int newAvailable = finalBook.getAvailable() - 1;
                    finalBook.setQuantity(newQuantity);
                    finalBook.setAvailable(newAvailable);
                    tvDetailQuantity.setText("Quantity: " + newQuantity);
                    tvDetailAvailable.setText("Available: " + newAvailable);
                    String borrowInfo = "Mượn thành công: " + finalBook.getTitle() + " (ID: " + finalBook.getId() + ")";
                    Toast.makeText(BookDetailActivity.this, borrowInfo, Toast.LENGTH_SHORT).show();

                    // Cập nhật dữ liệu chung
                    Book originalBook = BookManager.getBookById(finalBook.getId());
                    if (originalBook != null) {
                        originalBook.setQuantity(newQuantity);
                        originalBook.setAvailable(newAvailable);
                    }
                    BookManager.borrowedBooks.add(finalBook.getId());
                    BookManager.historyList.add(finalBook);
                    BookManager.notifyBookChanged(finalBook.getId());
                } else {
                    Toast.makeText(BookDetailActivity.this, "Sách đã hết, không thể mượn!", Toast.LENGTH_SHORT).show();
                }
            });

            // Đăng ký listener để cập nhật khi sách thay đổi
            BookManager.setOnBookChangeListener(bookId1 -> {
                if (bookId1 == bookId) {
                    Book updatedBook = BookManager.getBookById(bookId);
                    if (updatedBook != null) {
                        tvDetailQuantity.setText("Quantity: " + updatedBook.getQuantity());
                        tvDetailAvailable.setText("Available: " + updatedBook.getAvailable());
                    }
                }
            });
        }
    }
}