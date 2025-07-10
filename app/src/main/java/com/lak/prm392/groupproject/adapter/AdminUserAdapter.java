package com.lak.prm392.groupproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.data.local.entities.User;

import java.util.ArrayList;
import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.UserViewHolder> {

    private List<User> userList = new ArrayList<>();
    private final OnUserActionListener listener;

    public interface OnUserActionListener {
        void onBanUnbanClicked(User user);
    }

    public AdminUserAdapter(OnUserActionListener listener) {
        this.listener = listener;
    }

    public void setUserList(List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_admin, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = userList.get(position);
        holder.tvName.setText(u.name);
        holder.tvRole.setText("Vai trò: " + u.role);
        holder.tvStatus.setText(u.isBanned ? "Trạng thái: BỊ BAN" : "Trạng thái: Bình thường");
        holder.btnBanUnban.setText(u.isBanned ? "Unban" : "Ban");

        holder.btnBanUnban.setOnClickListener(v -> listener.onBanUnbanClicked(u));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRole, tvStatus;
        Button btnBanUnban;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnBanUnban = itemView.findViewById(R.id.btnBanUnban);
        }
    }
}

