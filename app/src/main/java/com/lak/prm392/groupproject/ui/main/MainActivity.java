package com.lak.prm392.groupproject.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.work.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.data.local.database.AppDatabase;
import com.lak.prm392.groupproject.data.local.entities.Review;
import com.lak.prm392.groupproject.data.local.entities.User;
import com.lak.prm392.groupproject.worker.ReminderWorker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private String userRole;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        userRole = getIntent().getStringExtra("user_role");

        Toast.makeText(this, "Đăng nhập với vai trò: " + userRole, Toast.LENGTH_SHORT).show();

        // Ẩn menu tab theo role
        if (!"teacher".equalsIgnoreCase(userRole)) {
            bottomNavigationView.getMenu().removeItem(R.id.menu_request);
        }
        if (!"admin".equalsIgnoreCase(userRole)) {
            bottomNavigationView.getMenu().removeItem(R.id.menu_admin);
        }

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());

            // Insert user
            User user = new User();
            user.name = "Minh";
            user.email = "minh@example.com";
            user.password = "123";
            user.role = "student";
            db.userDao().insertUser(user);

            // Insert review
            Review review = new Review();
            review.userId = 1;
            review.bookId = 1;
            review.comment = "Giáo trình chi tiết, thực hành tốt!";
            review.rating = 4;
            review.createdAt = "2025-07-07";
            db.reviewDao().insertReview(review);
        }).start();

        loadFragment(new BookListFragment());

        // Navigation item
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    selectedFragment = new BookListFragment();
                } else if (id == R.id.menu_history) {
                    selectedFragment = new HistoryFragment();
                } else if (id == R.id.menu_request) {
                    selectedFragment = new RequestFragment();
                } else if (id == R.id.menu_admin) {
                    selectedFragment = new AdminFragment();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }
                return false;
            }
        });

        // WorkManager định kỳ
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest reminderWork = new PeriodicWorkRequest.Builder(
                ReminderWorker.class,
                1, TimeUnit.DAYS
        ).setConstraints(constraints).build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "BookReminder",
                ExistingPeriodicWorkPolicy.KEEP,
                reminderWork
        );
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }

    public class BookListFragment extends Fragment {
        public BookListFragment() {
            super(R.layout.fragment_book_list);
        }
    }
    public class HistoryFragment extends Fragment {
        public HistoryFragment() {
            super(R.layout.fragment_history);
        }
    }
    public class RequestFragment extends Fragment {
        public RequestFragment() {
            super(R.layout.fragment_request);
        }
    }
    public class AdminFragment extends Fragment {
        public AdminFragment() {
            super(R.layout.fragment_admin);
        }
    }
}
