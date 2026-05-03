package com.marcwendt.libarySim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcwendt.libarySim.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    // leer lassen – Spring macht den Rest
}
