package com.lak.prm392.groupproject.ui.main;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.worker.ReminderWorker;

public class MainActivity extends AppCompatActivity {


    // chạy định kì để check thời hạn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo WorkManager chỉ 1 lần duy nhất
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