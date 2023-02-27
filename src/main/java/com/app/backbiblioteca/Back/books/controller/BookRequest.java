package com.app.backbiblioteca.Back.books.controller;


import lombok.Getter;


import java.sql.Date;

@Getter
public class BookRequest {
    private int id,edad,numeroPaginas, copias,edicion;
    private String titulo, autores, isbn, editorial,lenguaPublicacion,lenguaTraduccion,descripcion,formato,genero;
    private Date fechaEdicion;
    private String portada, imagen2,imagen3;
}
