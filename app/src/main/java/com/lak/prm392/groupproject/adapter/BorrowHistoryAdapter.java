package com.lak.prm392.groupproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toshokan.model.Book;
import com.lak.prm392.groupproject.R;

import java.util.List;

public class BorrowHistoryAdapter extends RecyclerView.Adapter<BorrowHistoryAdapter.ViewHolder> {
    private final List<Book> historyList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public BorrowHistoryAdapter(List<Book> historyList, OnItemClickListener listener) {
        this.historyList = historyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_borrow_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = historyList.get(position);
        holder.imgBook.setImageResource(book.getImageResId());
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText("Author: " + book.getAuthor());
        holder.tvQuantity.setText("Quantity: " + book.getQuantity());
        holder.tvAvailable.setText("Available: " + book.getAvailable());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(book);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView tvTitle, tvAuthor, tvQuantity, tvAvailable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvAvailable = itemView.findViewById(R.id.tvAvailable);
        }
    }
}