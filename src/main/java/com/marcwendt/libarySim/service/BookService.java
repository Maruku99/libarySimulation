package com.marcwendt.libarySim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.marcwendt.libarySim.exception.BookAlreadyBorrowedException;
import com.marcwendt.libarySim.exception.BookNotFoundException;
import com.marcwendt.libarySim.model.Book;
import com.marcwendt.libarySim.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Alle Bücher zurückgeben
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Einzelnes Buch suchen
    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // Buch verfügbarkeit prüfen
    public List<Book> getAvailableBooks() {
        return bookRepository.findByIsAvailable(true);
    }

    // Buch hinzufügen
    public Book addBook(@NonNull Book book) {
        return bookRepository.save(book);
    }

    // Buch löschen
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    // Buch ausleihen
    public void borrowBookById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (!book.isAvailable()) {
            throw new BookAlreadyBorrowedException(id);
        }

        book.setAvailable(false);
        bookRepository.save(book);
    }

    // Buch zurückgeben
    public String returnBookById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setAvailable(true);
        bookRepository.save(book);
        return "Buch wurde zurückgegeben";
    }
}
