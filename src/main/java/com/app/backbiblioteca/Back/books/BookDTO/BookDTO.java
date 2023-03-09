package com.app.backbiblioteca.Back.books.BookDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class BookDTO {
    private int id,edad,numeroPaginas, copias,edicion;
    private String titulo, autores, isbn, editorial,lenguaPublicacion,lenguaTraduccion,descripcion,formato,genero;
    private Date fechaEdicion;
    private String portada, imagen2,imagen3;

}

