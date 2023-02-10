package com.app.backbiblioteca.Back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDTO {
    int numero, id, edad,paginas, edicion;
    String titulo, imagen, ISBN, autor, editorial,fechaEdicion, lenguaPublicacion,lenguaTraduccion,formato,genero ;


}

