package com.app.backbiblioteca.Back.books.controller;


import lombok.Getter;


import java.sql.Date;

@Getter
public class BookRequest {
    private int numero, id, edad,paginas, edicion;
    private Date fechaEdicion;
    private String titulo, imagen, isbn, autor, editorial,
            lenguaPublicacion,lenguaTraduccion,formato,genero,descripcion ;
}
