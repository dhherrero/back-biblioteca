package com.app.backbiblioteca.Back.books.controller;


import lombok.Getter;


import java.sql.Date;

@Getter
public class BookRequest {
    private int id,edad,numeroPaginas, copias,edicion;
    private String titulo, autores, isbn, editorial,lenguaPublicacion,lenguaTraduccion,descripcion,formato,genero;
    private Date fechaEdicion;
    private String portada, imagen2,imagen3;

    @Override
    public String toString() {
        return "BookRequest{" +
                "id=" + id +
                ", edad=" + edad +
                ", numeroPaginas=" + numeroPaginas +
                ", copias=" + copias +
                ", edicion=" + edicion +
                ", titulo='" + titulo + '\'' +
                ", autores='" + autores + '\'' +
                ", isbn='" + isbn + '\'' +
                ", editorial='" + editorial + '\'' +
                ", lenguaPublicacion='" + lenguaPublicacion + '\'' +
                ", lenguaTraduccion='" + lenguaTraduccion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", formato='" + formato + '\'' +
                ", genero='" + genero + '\'' +
                ", fechaEdicion=" + fechaEdicion +
                ", portada='" + portada + '\'' +
                ", imagen2='" + imagen2 + '\'' +
                ", imagen3='" + imagen3 + '\'' +
                '}';
    }
}
