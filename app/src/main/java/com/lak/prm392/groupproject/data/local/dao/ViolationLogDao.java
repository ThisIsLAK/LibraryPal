package com.lak.prm392.groupproject.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.lak.prm392.groupproject.data.local.entities.StatEntry;
import com.lak.prm392.groupproject.data.local.entities.ViolationLog;

import java.util.List;
import java.util.Map;

@Dao
public interface ViolationLogDao {

    @Query("SELECT " +
            "CASE " +
            "WHEN :range = 'day' THEN date " +
            "WHEN :range = 'week' THEN strftime('%Y-%W', date) " +
            "WHEN :range = 'month' THEN strftime('%Y-%m', date) " +
            "END AS label, COUNT(*) as count " +
            "FROM ViolationLog " +
            "GROUP BY label " +
            "ORDER BY label")
    List<StatEntry> getStatsByRange(String range);

    @Query("SELECT * FROM ViolationLog WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<ViolationLog>> getViolationsByUser(int userId);

    @Insert
    void insert(ViolationLog log);

    @Query("SELECT * FROM ViolationLog ORDER BY date DESC")
    LiveData<List<ViolationLog>> getAllViolations();
}
