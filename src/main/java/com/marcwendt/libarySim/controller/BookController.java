package com.marcwendt.libarySim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcwendt.libarySim.model.Book;
import com.marcwendt.libarySim.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String getAllBooks() {
        return "get all books";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable long id){
        return "get book by id " + id;
    }

    @PostMapping
    public String addBook(@RequestBody Book book){
        return book.toString();
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable long id){
        return "delete " + id;
    }

    @PostMapping("/{id}/borrow")
    public String borrowBook(@PathVariable long id){
        return "Borrow Book" + id;
    }

    @PostMapping("/{id}/return")
    public String returnBook(@PathVariable long id){
        return "returned book";
    }

}
