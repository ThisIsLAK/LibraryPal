package com.lak.prm392.groupproject.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.worker.ReminderWorker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private String userRole; // student / teacher / admin
    private BottomNavigationView bottomNavigationView; // nếu bạn có dùng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔐 Lấy role từ intent truyền từ LoginActivity
        userRole = getIntent().getStringExtra("user_role");

        // 📦 Gắn logic phân quyền (VD: toast tạm thời test)
        Toast.makeText(this, "Bạn đang đăng nhập với quyền: " + userRole, Toast.LENGTH_SHORT).show();

        // Nếu bạn dùng BottomNavigationView thì có thể tùy biến menu ở đây
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (bottomNavigationView != null) {
            if (!"admin".equals(userRole)) {
                bottomNavigationView.getMenu().removeItem(R.id.menu_admin); // ẩn tab Admin nếu không phải admin
            }
            if (!"teacher".equals(userRole)) {
                bottomNavigationView.getMenu().removeItem(R.id.menu_request); // nếu có tab yêu cầu sách cho teacher
            }
        }

        // 🕑 Chạy định kỳ WorkManager nhắc hạn trả sách (đã có sẵn)
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest reminderWork = new PeriodicWorkRequest.Builder(
                ReminderWorker.class,
                1, TimeUnit.DAYS // chạy mỗi ngày
        ).setConstraints(constraints).build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "BookReminder",
                ExistingPeriodicWorkPolicy.KEEP,
                reminderWork
        );
    }
}
