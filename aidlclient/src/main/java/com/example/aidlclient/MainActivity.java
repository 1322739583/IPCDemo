package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidldemo.Book;
import com.example.aidldemo.BookController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnGetBooks)
    Button btnGetBooks;
    @BindView(R.id.btnAddBook)
    Button btnAddBook;

    private BookController bookController;
    private boolean mConn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("MainActivity", "连接成功");
            bookController = BookController.Stub.asInterface(service);
            mConn = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "断开连接");
            mConn = false;

        }
    };

    @OnClick({R.id.btnGetBooks, R.id.btnAddBook})
    public void onClick(View view) {
        tryBindService();
        switch (view.getId()) {
            case R.id.btnGetBooks:
                if (bookController==null){
                  Log.d("MainActivity", "book controller is null");
                    return;
                }

                try {
                    List<Book> bookList = bookController.getBookList();
                     Log.d("MainActivity", "bookList:" + bookList);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnAddBook:
                if (bookController==null){
                    Log.d("MainActivity", "book controller is null");
                    return;
                }

                try {
                    bookController.addBookInout(new Book("xxx"));
                    Log.d("MainActivity", "bookList:" + bookController.getBookList());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void tryBindService(){
        if (!mConn) {
            Intent intent = new Intent();
            intent.setPackage("com.example.aidldemo");
            intent.setAction("com.server");
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void tryUnbindService(){
        if (mConn){
            unbindService(serviceConnection);
            mConn =false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tryBindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tryUnbindService();
    }
}
