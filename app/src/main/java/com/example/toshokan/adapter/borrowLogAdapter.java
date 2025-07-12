package com.example.toshokan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshokan.R;
import com.example.toshokan.model.BorrowLog;

import java.util.List;

public class borrowLogAdapter extends ArrayAdapter<BorrowLog> {
    private Context context;
    private List<BorrowLog> logs;
    private OnReturnClickListener listener;

    // ✅ Interface callback để gọi từ Activity
    public interface OnReturnClickListener {
        void onReturnClick(BorrowLog log);
    }

    // ✅ Constructor
    public borrowLogAdapter(Context context, List<BorrowLog> logs, OnReturnClickListener listener) {
        super(context, 0, logs);
        this.context = context;
        this.logs = logs;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BorrowLog log = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_borrow_log, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.tvBorrowBookTitle);
        TextView tvBorrowDate = convertView.findViewById(R.id.tvBorrowDate);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        // ✅ Set dữ liệu từ log
        tvTitle.setText(log.getBookTitle() != null ? log.getBookTitle() : "Không rõ");
        tvBorrowDate.setText("Ngày mượn: " + log.getBorrowDate());
        tvStatus.setText("Trạng thái: " + (log.isReturned() ? "Đã trả" : "Đang mượn"));

        // ✅ Nếu sách đang được mượn, set onClick trả
        if (!log.isReturned()) {
            convertView.setOnClickListener(v -> {
                // Gọi về listener để Activity xử lý
                if (listener != null) {
                    listener.onReturnClick(log);
                }
            });
        } else {
            convertView.setOnClickListener(null);
        }

        return convertView;
    }
}
