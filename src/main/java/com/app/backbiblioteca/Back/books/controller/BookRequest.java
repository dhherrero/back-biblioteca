package com.app.backbiblioteca.Back.books.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class BookRequest {
    private int numero, id, edad,paginas, edicion;
    private Date fechaEdicion;
    private String titulo, imagen, ISBN, autor, editorial,
            lenguaPublicacion,lenguaTraduccion,formato,genero,descripcion ;
}
