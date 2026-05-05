package com.marcwendt.libarySim.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("Buch mit ID " + id + " nicht gefunden");
    }
}
