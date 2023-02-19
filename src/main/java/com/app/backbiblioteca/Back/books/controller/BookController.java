package com.app.backbiblioteca.Back.books.controller;


import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.books.service.BookService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@CrossOrigin("http://localhost:5173/")
@RestController
@RequestMapping("/book")
public class BookController {

    private static BookService bookService= new BookService();

    @GetMapping("/prueba")
    public String probando(){
        return bookService.prueba();
    }

    @GetMapping("/new")
    public String newBook(){
        BookDTO libro2= BookDTO.builder().numero(3).id(102).edad(3).paginas(257).
                edicion(5).fechaEdicion(Date.valueOf("2002-02-02")).titulo("Incertidumbre").
                imagen("imagen").ISBN("ISBN").autor("Gerardo").editorial("Planeta 3").lenguaPublicacion("Francés").
                lenguaTraduccion("Español").formato("Medio").genero("Comedia").descripcion("jasjas").build();


        bookService.insertBook(libro2);
        return "OK";
    }
}
