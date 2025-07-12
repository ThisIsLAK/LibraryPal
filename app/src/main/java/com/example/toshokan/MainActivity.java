package com.example.toshokan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btnBookList, btnHistory;
    private TextView txvSelectedItem;

    // Launcher cho BookListActivity
    private final ActivityResultLauncher<Intent> activityResultLauncher_BookList =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                                Toast.makeText(MainActivity.this, "Quay lại từ danh sách sách", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    // Launcher cho BorrowHistoryActivity
    private final ActivityResultLauncher<Intent> activityResultLauncher_BorrowHistory =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                                Toast.makeText(MainActivity.this, "Quay lại từ lịch sử mượn", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Khởi tạo MainActivity");

        btnBookList = findViewById(R.id.btnViewBooks);
        btnHistory = findViewById(R.id.btnBorrowHistory);
        txvSelectedItem = findViewById(R.id.txvSelectedItem);

        btnBookList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookListActivity.class);
            Log.d(TAG, "btnBookList: Thử mở BookListActivity");
            startActivity(intent); // Sử dụng startActivity trực tiếp
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BorrowHistoryActivity.class);
            activityResultLauncher_BorrowHistory.launch(intent);
        });
    }
}
