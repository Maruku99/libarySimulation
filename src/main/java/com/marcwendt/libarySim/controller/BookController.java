package com.marcwendt.libarySim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @GetMapping("/books")
    public String helloBooks() {
		return String.format("Hello books");
	}

}
