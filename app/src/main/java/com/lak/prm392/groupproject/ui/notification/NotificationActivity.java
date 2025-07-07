package com.lak.prm392.groupproject.ui.notification;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.model.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationItem> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recycler_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo danh sách giả lập để test
        notificationList = new ArrayList<>();
        notificationList.add(new NotificationItem("Sách sắp đến hạn", "Bạn sắp đến hạn trả sách Java", "07/07/2025"));
        notificationList.add(new NotificationItem("Sách đã quá hạn", "Bạn đã trễ hạn sách C++", "06/07/2025"));

        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
    }
}
