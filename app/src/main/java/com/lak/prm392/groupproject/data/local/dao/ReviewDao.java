package com.lak.prm392.groupproject.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.lak.prm392.groupproject.data.local.entities.Review;

import java.util.List;

@Dao
public interface ReviewDao {
    @Insert
    void insertReview(Review review);

    @Query("SELECT * FROM Review WHERE bookId = :bookId")
    List<Review> getReviewsByBookId(int bookId);
}

