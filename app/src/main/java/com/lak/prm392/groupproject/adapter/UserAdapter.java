package com.lak.prm392.groupproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.data.local.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList = new ArrayList<>();
    private final OnUserActionListener listener;

    public interface OnUserActionListener {
        void onBanClicked(User user);
        void onUnbanClicked(User user);
    }

    public UserAdapter(OnUserActionListener listener) {
        this.listener = listener;
    }

    public void setUsers(List<User> list) {
        this.userList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = userList.get(position);
        holder.text1.setText(u.name + " (" + u.role + ")");
        String status = u.isBanned ? "BỊ KHÓA đến " + u.banUntil : "Hoạt động";
        holder.text2.setText("Email: " + u.email + " | " + status);

        holder.itemView.setOnClickListener(v -> {
            if (u.isBanned) listener.onUnbanClicked(u);
            else listener.onBanClicked(u);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}

