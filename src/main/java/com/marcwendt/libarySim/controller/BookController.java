package com.marcwendt.libarySim.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcwendt.libarySim.model.Book;

@RestController
public class BookController {
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Clean Code", "Robert C. Martin", true));
        books.add(new Book(2, "Der Hobbit", "J.R.R. Tolkien", true));
        books.add(new Book(3, "1984", "George Orwell", false));
        return books;
    }

}
