package com.app.backbiblioteca.Back.books.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class BookDTO {
    private int numero, id, edad,paginas, edicion;
    private Date fechaEdicion;
    private String titulo, imagen, ISBN, autor, editorial, lenguaPublicacion,lenguaTraduccion,formato,genero ;


}

