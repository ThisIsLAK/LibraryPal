package com.lak.prm392.groupproject.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.data.local.entities.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdminAdapter extends RecyclerView.Adapter<BookAdminAdapter.BookViewHolder> {
    private List<Book> bookList = new ArrayList<>();
    private final OnBookActionListener listener;

    public interface OnBookActionListener {
        void onEdit(Book book);
        void onDelete(Book book);
    }

    public BookAdminAdapter(OnBookActionListener listener) {
        this.listener = listener;
    }

    public void setBookList(List<Book> books) {
        this.bookList = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_admin, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book b = bookList.get(position);
        holder.tvTitle.setText(b.title);
        holder.tvAuthor.setText("Tác giả: " + b.author);
        holder.tvQuantity.setText("Số lượng: " + b.quantity);

        if (b.imageUri != null) {
            holder.ivBookImage.setImageURI(Uri.parse(b.imageUri));
        } else {
            holder.ivBookImage.setImageResource(R.drawable.ic_book_placeholder);
        }


        holder.btnEdit.setOnClickListener(v -> listener.onEdit(b));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(b));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor, tvQuantity;
        Button btnEdit, btnDelete;
        ImageView ivBookImage;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            ivBookImage = itemView.findViewById(R.id.ivBookImage);
        }
    }
}

