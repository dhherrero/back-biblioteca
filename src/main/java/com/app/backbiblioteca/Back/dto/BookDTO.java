package com.app.backbiblioteca.Back.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookDTO {
    int numero, id, edad,paginas, edicion;
    String titulo, imagen, ISBN, autor, editorial,fechaEdicion, lenguaPublicacion,lenguaTraduccion,formato,genero ;

    public BookDTO(int numero, int id, int edad, int paginas, int edicion, String titulo, String imagen, String ISBN, String autor, String editorial, String fechaEdicion, String lenguaPublicacion, String lenguaTraduccion, String formato, String genero) {
        this.numero = numero;
        this.id = id;
        this.edad = edad;
        this.paginas = paginas;
        this.edicion = edicion;
        this.titulo = titulo;
        this.imagen = imagen;
        this.ISBN = ISBN;
        this.autor = autor;
        this.editorial = editorial;
        this.fechaEdicion = fechaEdicion;
        this.lenguaPublicacion = lenguaPublicacion;
        this.lenguaTraduccion = lenguaTraduccion;
        this.formato = formato;
        this.genero = genero;
    }
}
