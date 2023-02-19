package com.app.backbiblioteca.Back.books.service;

import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.config.DatabaseConfig;
import com.sun.deploy.net.HttpResponse;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@NoArgsConstructor
public class BookService {
    private final Logger logger= LogManager.getLogger(this.getClass());
    private static DatabaseConfig db = new DatabaseConfig();


    public String prueba() {
        String sql = "SELECT  * FROM libros", result =null;
        try(PreparedStatement pst= db.statement(sql)) {
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                result= rs.getString("titulo");
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return result;
    }

    public void insertBook(BookDTO book){
        String sql= "INSERT INTO libros (numero, titulo, imagen, id, isbn, edad, autores, editorial, fechaEdicion, lenguaPublicacion, lenguaTraduccion, numeroPaginas, descripcion, edicion, formato, genero) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int numero= book.getNumero(), id= book.getId(), edad= book.getEdad(),
                edicion= book.getEdicion(), numeroPaginas= book.getPaginas();

        Date fechaEdicion= book.getFechaEdicion();

        String titulo= book.getTitulo(), imagen= book.getImagen(), isbn= book.getISBN(),
                autores=book.getAutor(),editorial= book.getEditorial(),
                lenguaPublicacion= book.getLenguaPublicacion(), lenguaTraduccion= book.getLenguaTraduccion(),
                descripcion= book.getDescripcion(), formato= book.getFormato(), genero= book.getGenero();

        try(PreparedStatement pst= db.statement(sql)) {
            pst.setInt(1,numero);
            pst.setString(2,titulo);
            pst.setString(3,imagen);
            pst.setInt(4,id);
            pst.setString(5,isbn);
            pst.setInt(6,edad);
            pst.setString(7,autores);
            pst.setString(8,editorial);
            pst.setDate(9,fechaEdicion);
            pst.setString(10,lenguaPublicacion);
            pst.setString(11, lenguaTraduccion);
            pst.setInt(12,numeroPaginas);
            pst.setString(13,descripcion);
            pst.setInt(14,edicion);
            pst.setString(15,formato);
            pst.setString(16,genero);

            boolean rs= pst.execute();
            logger.info("RES::: "+ rs);

        } catch (SQLException throwables) {
            logger.error(throwables);
        }



    }


}
