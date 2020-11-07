package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {

    private List<Book> bookList;


    @Override
    public void onCreate() {
        bookList=new ArrayList<>();
        initData();
    }

    private void initData() {
        for (int i = 0; i <10 ; i++) {
            bookList.add(new Book("book"+i));
        }

    }

    BookController.Stub stub=new BookController.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBookInout(Book book) throws RemoteException {
               bookList.add(book);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
