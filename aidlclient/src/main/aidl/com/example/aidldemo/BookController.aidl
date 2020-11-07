// BookController.aidl
package com.example.aidldemo;
import com.example.aidldemo.Book;


// Declare any non-default types here with import statements

interface BookController {

    List<Book> getBookList();

    void addBookInout(in Book book);
}
