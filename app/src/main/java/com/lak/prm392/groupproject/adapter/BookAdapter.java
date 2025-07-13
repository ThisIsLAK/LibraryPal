package com.lak.prm392.groupproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toshokan.BookDetailActivity;
import com.example.toshokan.BookManager;
import com.example.toshokan.model.Book;
import com.lak.prm392.groupproject.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private final Context context;
    private final List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList; // Sử dụng danh sách từ BookManager
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = BookManager.bookList.get(position); // Lấy dữ liệu trực tiếp từ BookManager
        holder.imgBook.setImageResource(book.getImageResId());
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText("Author: " + book.getAuthor());
        holder.tvQuantity.setText("Quantity: " + book.getQuantity());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra("book_id", book.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return BookManager.bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView tvTitle, tvAuthor, tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}