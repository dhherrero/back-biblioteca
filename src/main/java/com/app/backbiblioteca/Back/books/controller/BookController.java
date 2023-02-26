package com.app.backbiblioteca.Back.books.controller;


import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/book")
public class BookController {

    private static BookService bookService= new BookService();

    private static final String OK= "ACTION WAS SUCCESFUL";
    private static final String CREATED= "BOOK CREATED";
    private static final String NOT_SUCCESFUL= "ACTION WAS NOT SUCCESFUL";
    private static final String NOT_FOUND= "BOOK NOT FOUND";


    public ResponseEntity<?> getResponse(HttpStatus httpStatus){
        Map<HttpStatus,ResponseEntity<?>> mapResponse= new HashMap<>();
        mapResponse.put(HttpStatus.OK,new ResponseEntity<>(OK,HttpStatus.OK));
        mapResponse.put(HttpStatus.CREATED,new ResponseEntity<>(CREATED,HttpStatus.CREATED));
        mapResponse.put(HttpStatus.NOT_ACCEPTABLE,new ResponseEntity<>(NOT_SUCCESFUL,HttpStatus.NOT_ACCEPTABLE));
        return mapResponse.get(httpStatus);
    }

    public BookDTO savePayloadInBook (BookRequest payload){
        BookDTO libro= BookDTO.builder().numero(payload.getNumero()).id(payload.getId()).edad(payload.getEdad()).
                paginas(payload.getPaginas()).edicion(payload.getEdicion()).fechaEdicion(payload.getFechaEdicion()).
                titulo(payload.getTitulo()).imagen(payload.getImagen()).isbn(payload.getIsbn()).autor(payload.getAutor()).
                editorial(payload.getEditorial()).lenguaPublicacion(payload.getLenguaPublicacion()).descripcion(payload.getDescripcion()).
                lenguaTraduccion(payload.getLenguaTraduccion()).formato(payload.getFormato()).genero(payload.getGenero()).build();
        return libro;
    }

    @PostMapping("/newBook")
    public ResponseEntity<?> newBook(@RequestBody BookRequest payload){
        BookDTO libro= savePayloadInBook(payload);
        HttpStatus response= bookService.insertBook(libro);
        return getResponse(response);
    }

    @PostMapping("/getBook")
    public ResponseEntity<?> readBook(@RequestBody BookRequest payload){
        Object response = bookService.readBook(payload.getNumero());
        if (HttpStatus.NOT_FOUND==response){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/deleteBook")
    public ResponseEntity<?> deleteBook(@RequestBody BookRequest payload){
        HttpStatus response = bookService.deleteBook(payload.getNumero());
        return getResponse(response);
    }

    @GetMapping("/allBooks")
    public ResponseEntity<?> allBooks(@RequestParam String orderBy){
        ArrayList<BookDTO> response = bookService.allBooks(orderBy);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
