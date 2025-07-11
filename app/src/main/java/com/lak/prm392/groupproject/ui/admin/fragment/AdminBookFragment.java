package com.lak.prm392.groupproject.ui.admin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.adapter.BookAdminAdapter;
import com.lak.prm392.groupproject.data.local.entities.Book;
import com.lak.prm392.groupproject.model.AdminViewModel;

public class AdminBookFragment extends Fragment {

    private AdminViewModel viewModel;
    private BookAdminAdapter adapter;
    private Uri selectedImageUri;
    private ImageView imgPreview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_book, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvAdminBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdminAdapter(new BookAdminAdapter.OnBookActionListener() {
            @Override
            public void onEdit(Book book) {
                showBookDialog(book); // sửa
            }

            @Override
            public void onDelete(Book book) {
                viewModel.deleteBook(book);
                Toast.makeText(getContext(), "Đã xoá sách", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        viewModel.getAllBooks().observe(getViewLifecycleOwner(), adapter::setBookList);

        // FAB Thêm sách
        FloatingActionButton fab = view.findViewById(R.id.fabAddBook);
        fab.setOnClickListener(v -> showBookDialog(null));

        return view;
    }

    private void showBookDialog(Book existingBook) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_book_form, null);
        EditText etTitle = dialogView.findViewById(R.id.etTitle);
        EditText etAuthor = dialogView.findViewById(R.id.etAuthor);
        EditText etQuantity = dialogView.findViewById(R.id.etQuantity);

        if (existingBook != null) {
            etTitle.setText(existingBook.title);
            etAuthor.setText(existingBook.author);
            etQuantity.setText(String.valueOf(existingBook.quantity));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(existingBook == null ? "Thêm sách mới" : "Sửa sách")
                .setView(dialogView)
                .setNegativeButton("Huỷ", null)
                .setPositiveButton(existingBook == null ? "Thêm" : "Cập nhật", null);

        AlertDialog dialog = builder.create();
        Button btnSelectImage = dialogView.findViewById(R.id.btnSelectImage);
        imgPreview = dialogView.findViewById(R.id.ivBookImage);

// Gắn ảnh preview nếu đang sửa
        if (existingBook != null && existingBook.imageUri != null) {
            selectedImageUri = Uri.parse(existingBook.imageUri);
            imgPreview.setImageURI(selectedImageUri);
        } else {
            imgPreview.setImageResource(R.drawable.ic_book_placeholder); // icon mặc định
        }

// Gắn onClick cho nút chọn ảnh
        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String title = etTitle.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                String qtyStr = etQuantity.getText().toString().trim();

                if (title.isEmpty() || author.isEmpty() || qtyStr.isEmpty()) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantity = Integer.parseInt(qtyStr);

                if (existingBook == null) {
                    Book newBook = new Book(title, author, quantity);
                    newBook.imageUri = selectedImageUri != null ? selectedImageUri.toString() : null;
                    viewModel.insertBook(newBook);
                    Toast.makeText(getContext(), "Đã thêm sách", Toast.LENGTH_SHORT).show();
                } else {
                    existingBook.title = title;
                    existingBook.author = author;
                    existingBook.quantity = quantity;
                    existingBook.imageUri = selectedImageUri != null ? selectedImageUri.toString() : null;
                    viewModel.updateBook(existingBook);
                    Toast.makeText(getContext(), "Đã cập nhật", Toast.LENGTH_SHORT).show();
                }

                if (selectedImageUri == null && existingBook != null && existingBook.imageUri != null) {
                    selectedImageUri = Uri.parse(existingBook.imageUri);
                }

                dialog.dismiss();
            });
        });

        dialog.show();
    }
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (imgPreview != null && selectedImageUri != null)
                        imgPreview.setImageURI(selectedImageUri);
                }
            }
    );

}
