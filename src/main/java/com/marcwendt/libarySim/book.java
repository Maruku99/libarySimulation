package com.marcwendt.libarySim;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int id, String title, String author, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public String printBook(Book book) {
        return book.toString();
    }

    @Override
    public String toString() {
        return String.format("[%d]; [%s] ist der Autor von [%s], Verfügbar[%s]",id, author, title, isAvailable ? "Ja" : "Nein");
    }
}
