package com.marcwendt.libarySim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Buch nicht gefunden"));
    }

    // Buch hinzufügen
    public Book addBook(@NonNull Book book) {
        return bookRepository.save(book);
    }

    // Buch löschen
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

}
