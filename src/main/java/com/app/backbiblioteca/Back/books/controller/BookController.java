package com.app.backbiblioteca.Back.books.controller;


import com.app.backbiblioteca.Back.books.service.BookService;
import com.app.backbiblioteca.Back.config.DatabaseConfig;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:5173/")
@RestController
@RequestMapping("/book")
public class BookController {

    private static BookService bookService= new BookService();
    @GetMapping("/prueba")
    public String probando(){
        return bookService.prueba();
    }
}
