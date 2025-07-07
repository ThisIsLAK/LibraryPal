package com.lak.prm392.groupproject.ui.review;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.data.local.entities.Review;
import com.lak.prm392.groupproject.data.repository.ReviewRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookReviewActivity extends AppCompatActivity {
    private int bookId, userId;
    private EditText commentEditText;
    private RatingBar ratingBar;
    private Button submitButton;
    private ReviewRepository reviewRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_review);

        reviewRepository = new ReviewRepository(this);
        bookId = getIntent().getIntExtra("book_id", -1);
        userId = getIntent().getIntExtra("user_id", -1);

        commentEditText = findViewById(R.id.editTextComment);
        ratingBar = findViewById(R.id.ratingBar);
        submitButton = findViewById(R.id.buttonSubmitReview);

        submitButton.setOnClickListener(v -> {
            String comment = commentEditText.getText().toString();
            int stars = (int) ratingBar.getRating();

            Review review = new Review();
            review.bookId = bookId;
            review.userId = userId;
            review.comment = comment;
            review.rating = stars;
            review.createdAt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            new Thread(() -> {
                reviewRepository.addReview(review);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
