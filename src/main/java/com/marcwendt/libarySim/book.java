package com.marcwendt.libarySim;

class Book {
    private int id;
    private int isbn;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int id, int isbn, String title, String author, boolean isAvailable) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public String printBook(Book book) {
        // System.out.println(book.toString());
        return book.toString();
    }

    @Override
    public String toString() {
        return String.format("[%d]; [%s] ist der Autor von [%s] mit der ISBN [%d], Verfügbar[%s]",id, author, title, isbn, isAvailable ? "Ja" : "Nein");
    }
}
