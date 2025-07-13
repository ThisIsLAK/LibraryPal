package com.lak.prm392.groupproject.ui.book;

import com.example.toshokan.model.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookManager {
    public static List<Book> bookList = new ArrayList<>();
    public static Set<Integer> borrowedBooks = new HashSet<>();
    public static List<Book> historyList = new ArrayList<>();

    public interface OnBookChangeListener {
        void onBookChanged(int bookId);
    }

    private static OnBookChangeListener changeListener;

    static {
        // Khởi tạo danh sách sách
        bookList.add(new Book(1, "Mimi kara oboeru", "Satoru Kanamori", 9, R.drawable.mimikara, "Sách luyện nghe hiệu quả", 9));
        bookList.add(new Book(2, "Kanji Master", "Kikuko Otake", 5, R.drawable.kanji_master, "Sách luyện Hán tự", 5));
        bookList.add(new Book(3, "Nama Chuukei", "Tomoko", 4, R.drawable.nama, "Sách nghe – nói", 4));
        bookList.add(new Book(4, "Shinkanzen", "Mikiko Imai", 8, R.drawable.shinkanzen, "Bộ sách luyện thi JLPT", 8));
        bookList.add(new Book(5, "Try", "Atsuko Fukuda", 6, R.drawable.try_n2, "Sách học ngữ pháp", 6));
        bookList.add(new Book(6, "Soumatome n3", "Hiroshi Tanaka", 6, R.drawable.soumatome, "Sách tổng hợp N3", 6));
        bookList.add(new Book(7, "Goukaku Dekiru Nihongo", "Yuki Sato", 6, R.drawable.goukaku_dekiru_nihongo, "Sách ngữ pháp JLPT", 6));
        bookList.add(new Book(8, "Dokkai Pointo", "Kenji Yamada", 6, R.drawable.dokkai_pointo, "Sách đọc hiểu", 6));
        bookList.add(new Book(9, "Pattern Betsu", "Naomi Suzuki", 6, R.drawable.patternbetsu, "Sách phân tích ngữ pháp", 6));
    }

    public static Book getBookById(int id) {
        for (Book book : bookList) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    public static void setOnBookChangeListener(OnBookChangeListener listener) {
        changeListener = listener;
    }

    public static void notifyBookChanged(int bookId) {
        if (changeListener != null) {
            changeListener.onBookChanged(bookId);
        }
    }
}