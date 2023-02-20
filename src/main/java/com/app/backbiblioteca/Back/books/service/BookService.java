package com.app.backbiblioteca.Back.books.service;

import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.config.DatabaseConfig;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
@NoArgsConstructor
public class BookService {
    private final Logger logger= LogManager.getLogger(this.getClass());
    private static DatabaseConfig db = new DatabaseConfig();

    private BookDTO saveInBook(ResultSet rs) throws SQLException {
        BookDTO book= BookDTO.builder().numero(rs.getInt("numero")).
                id(rs.getInt("id")).edad(rs.getInt("edad")).
                paginas(rs.getInt("numeroPaginas")).
                edicion(rs.getInt("edicion")).fechaEdicion(rs.getDate("fechaEdicion")).
                titulo(rs.getString("titulo")).
                imagen(rs.getString("imagen")).ISBN(rs.getString("isbn")).
                autor(rs.getString("autores")).editorial(rs.getString("editorial")).
                lenguaPublicacion(rs.getString("lenguaPublicacion")).
                lenguaTraduccion(rs.getString("lenguaTraduccion")).formato(rs.getString("formato")).
                genero(rs.getString("genero")).build();
        return book;
    }

    public HttpStatus insertBook(BookDTO book){
        String sql= "INSERT INTO libros (numero, titulo, imagen, id, isbn, edad, autores, editorial, fechaEdicion, lenguaPublicacion, lenguaTraduccion, numeroPaginas, descripcion, edicion, formato, genero) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int numero= book.getNumero(), id= book.getId(), edad= book.getEdad(),
                edicion= book.getEdicion(), numeroPaginas= book.getPaginas();

        Date fechaEdicion= book.getFechaEdicion();

        String titulo= book.getTitulo(), imagen= book.getImagen(), isbn= book.getISBN(),
                autores=book.getAutor(),editorial= book.getEditorial(),
                lenguaPublicacion= book.getLenguaPublicacion(), lenguaTraduccion= book.getLenguaTraduccion(),
                descripcion= book.getDescripcion(), formato= book.getFormato(), genero= book.getGenero();

        try(PreparedStatement pst= db.statement(sql)) {
            pst.setInt(1,numero); pst.setString(2,titulo);
            pst.setString(3,imagen); pst.setInt(4,id);
            pst.setString(5,isbn); pst.setInt(6,edad);
            pst.setString(7,autores); pst.setString(8,editorial);
            pst.setDate(9,fechaEdicion); pst.setString(10,lenguaPublicacion);
            pst.setString(11, lenguaTraduccion); pst.setInt(12,numeroPaginas);
            pst.setString(13,descripcion); pst.setInt(14,edicion);
            pst.setString(15,formato); pst.setString(16,genero);
            pst.execute();

        } catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
            return HttpStatus.CREATED; }

    public ArrayList <BookDTO> allBooks(){
        String sql ="SELECT * FROM libros";
        ArrayList <BookDTO> listaLibros = new ArrayList<>();
        try(PreparedStatement pst= db.statement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                BookDTO book= saveInBook(rs);
                listaLibros.add(book);
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return listaLibros;
    }

    public BookDTO readBook(int numero){
        String sql ="SELECT * FROM libros where numero =?";
        BookDTO libro=null;
        try(PreparedStatement pst= db.statement(sql)) {
            pst.setInt(1,numero);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                libro= saveInBook(rs);
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return libro;
    }
}
