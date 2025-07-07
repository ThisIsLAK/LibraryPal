package com.lak.prm392.groupproject.data.repository;

import android.content.Context;

import com.lak.prm392.groupproject.data.local.dao.ReviewDao;
import com.lak.prm392.groupproject.data.local.database.AppDatabase;
import com.lak.prm392.groupproject.data.local.entities.Review;

import java.util.List;

public class ReviewRepository {
    private ReviewDao reviewDao;

    public ReviewRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        reviewDao = db.reviewDao();
    }

    public void addReview(Review review) {
        reviewDao.insertReview(review);
    }

    public List<Review> getReviewsByBookId(int bookId) {
        return reviewDao.getReviewsByBookId(bookId);
    }
}

