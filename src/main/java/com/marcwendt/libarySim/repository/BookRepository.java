package com.marcwendt.libarySim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcwendt.libarySim.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsAvailable(boolean isAvailable);
}
