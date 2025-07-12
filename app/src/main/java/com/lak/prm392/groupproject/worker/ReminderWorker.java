package com.lak.prm392.groupproject.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.lak.prm392.groupproject.R;

public class ReminderWorker extends Worker {

    private static final String CHANNEL_ID = "book_reminder_channel";

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // TODO: Thay đoạn kiểm tra BorrowLog từ DB bằng logic thực tế
        // Tạm giả lập có một cuốn sách sắp đến hạn
        String bookTitle = "Lập trình Java";
        String dueDate = "08/07/2025";

        String message = "\uD83D\uDCDA Bạn sắp đến hạn trả sách: " + bookTitle + " vào ngày " + dueDate;
        sendNotification("Nhắc hạn trả sách", message);

        return Result.success();
    }

    private void sendNotification(String title, String message) {
        NotificationManager manager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Nhắc hạn trả sách",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_book_placeholder)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
