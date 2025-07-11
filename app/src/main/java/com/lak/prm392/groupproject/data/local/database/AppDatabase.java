package com.lak.prm392.groupproject.data.local.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lak.prm392.groupproject.data.local.dao.ReviewDao;
import com.lak.prm392.groupproject.data.local.dao.UserDao;
import com.lak.prm392.groupproject.data.local.entities.Review;
import com.lak.prm392.groupproject.data.local.entities.User;

@Database(entities = {User.class, Review.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "library_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

    public abstract ReviewDao reviewDao();

}
