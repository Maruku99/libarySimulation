package com.marcwendt.libarySim.exception;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(long id) {
        super("Buch mit ID " + id + " ist bereits ausgeliehen");
    }
}
