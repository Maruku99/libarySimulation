package com.marcwendt.libarySim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class libarySimApplication {
	public static void main(String[] args) {
		SpringApplication.run(libarySimApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(/*@RequestParam(value = "name", defaultValue = "World") String name*/) {
		// return String.format("Hello %s! neues Buch: [%s]", name);
		return "Hello World";
	}

	@GetMapping("/book")
	public String book(){
		Book book = new Book(0, "Harry Otter", "der Stein der alte", true);
		return book.printBook(book);
	}
}
