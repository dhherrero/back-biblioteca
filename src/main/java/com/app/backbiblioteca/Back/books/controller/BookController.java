package com.app.backbiblioteca.Back.books.controller;


import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.books.service.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:5173/")
@RestController
@RequestMapping("/book")
public class BookController {

    private static BookService bookService= new BookService();

    private static final String OK= "ACTION WAS SUCCESFUL";
    private static final String CREATED= "BOOK CREATED";
    private static final String NOT_CREATED= "NOT CREATED";




    public ResponseEntity<?> getResponse(HttpStatus httpStatus){
        Map<HttpStatus,ResponseEntity<?>> mapResponse= new HashMap<>();
        mapResponse.put(HttpStatus.CREATED,new ResponseEntity<>(CREATED,HttpStatus.CREATED));
        mapResponse.put(HttpStatus.NOT_ACCEPTABLE,new ResponseEntity<>(NOT_CREATED,HttpStatus.NOT_ACCEPTABLE));
        return mapResponse.get(httpStatus);
    }

    @GetMapping("/new")
    public ResponseEntity<?> newBook(){
        BookDTO libro2= BookDTO.builder().numero(6).id(102).edad(3).paginas(257).
                edicion(5).fechaEdicion(Date.valueOf("2002-02-02")).titulo("Incertidumbre").
                imagen("imagen").ISBN("ISBN").autor("Gerardo").editorial("Planeta 3").lenguaPublicacion("Francés").
                lenguaTraduccion("Español").formato("Medio").genero("Comedia").build();


        HttpStatus response= bookService.insertBook(libro2);
        return getResponse(response);
    }

    @PostMapping("/getBook")
    public ResponseEntity<?> readBook(@RequestBody BookRequest payload){
        BookDTO response = bookService.readBook(payload.getNumero());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/library")
    public ResponseEntity<?> allBooks(){
        ArrayList<BookDTO> response = bookService.allBooks();
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

}
