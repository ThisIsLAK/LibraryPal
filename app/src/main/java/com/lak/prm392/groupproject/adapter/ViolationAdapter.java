package com.lak.prm392.groupproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.data.local.entities.ViolationLog;

import java.util.ArrayList;
import java.util.List;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder> {

    private List<ViolationLog> logs = new ArrayList<>();

    public void setViolationLogs(List<ViolationLog> newLogs) {
        this.logs = newLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_violation_log, parent, false);
        return new ViolationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        ViolationLog log = logs.get(position);
        holder.tvUserId.setText("User ID: " + log.userId);
        holder.tvReason.setText("Lý do: " + log.reason);
        holder.tvDate.setText("Ngày: " + log.date);
        holder.tvAction.setText("Hành động: " + log.actionTaken);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    static class ViolationViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserId, tvReason, tvDate, tvAction;

        public ViolationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAction = itemView.findViewById(R.id.tvAction);
        }
    }
}
