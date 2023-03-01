package com.app.backbiblioteca.Back.books.service;

import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.books.controller.BookRequest;
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
import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
public class BookService {
    private final Logger logger= LogManager.getLogger(this.getClass());
    private static DatabaseConfig db = new DatabaseConfig();
    private static final String BOOK_NOT_CREATED= "BOOK NOT CREATED";
    private static final String BOOK_NOT_DELETED= "BOOK NOT DELETED";
    private static final String PHOTO_ERROR= "PHOTO_NOT_CREATED";
    private static final String BOOK_CREATED= "BOOK CREATED";
    private static final String PHOTO_CREATED= "PHOTO CREATED";


    public String getOrder(String orderBy){
        Map<String,String> mapResponse= new HashMap<>();
        String order=" ORDER BY ";
        mapResponse.put("titulo",order+ "titulo");
        mapResponse.put("autor",order+ "autores");
        mapResponse.put("edad",order+ "edad");
        mapResponse.put("editorial",order+ "editorial");
        mapResponse.put("fecha",order+ "fechaEdicion");
        mapResponse.put("genero",order+ "genero");
        mapResponse.put("formato",order+ "formato");
        mapResponse.put("defecto"," ");
        return mapResponse.get(orderBy);
    }

    private BookDTO saveInBook(ResultSet rs) throws SQLException {
        BookDTO book= BookDTO.builder().id(rs.getInt("id")).titulo(rs.getString("titulo")).
                autores(rs.getString("autores")).isbn(rs.getString("isbn")).
                edad(rs.getInt("edad")).editorial(rs.getString("editorial")).
                fechaEdicion(rs.getDate("fechaEdicion")).lenguaPublicacion(rs.getString("lenguaPublicacion")).
                lenguaTraduccion(rs.getString("lenguaTraduccion")).numeroPaginas(rs.getInt("numeroPaginas")).
                descripcion(rs.getString("descripcion")).edicion(rs.getInt("edicion")).formato(rs.getString("formato")).
                genero(rs.getString("genero")).copias(rs.getInt("copias")).
                portada(rs.getString("portada")).imagen2(rs.getString("imagen2")).imagen3(rs.getString("imagen3")).build();

        return book;
    }

    public HttpStatus deleteBook(int id) {
        String sql= "DELETE FROM libro WHERE id=?";
        try(PreparedStatement pst= db.statement(sql)) {
            pst.setInt(1,id);
            pst.execute();
        }
        catch (SQLException throwables){
            logger.error(throwables);
            logger.error(BOOK_NOT_DELETED);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;
    }

    public HttpStatus insertImages(BookRequest book){
        String sql = "INSERT INTO imagenes (idLibro, portada, imagen2, imagen3) VALUES(LAST_INSERT_ID(), ?, ?, ?)";
        try(PreparedStatement pst= db.statement(sql)) {
            pst.setString(1,book.getPortada());
            pst.setString(2,book.getImagen2());
            pst.setString(3,book.getImagen3());
            pst.execute();
            logger.info(PHOTO_CREATED);
        }catch (SQLException throwables) {
            logger.error(throwables);
            logger.error(PHOTO_ERROR);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.CREATED;

    }


    public HttpStatus insertBook(BookRequest book){

        String sql ="INSERT INTO libro (titulo, autores, isbn, edad, editorial, fechaEdicion, lenguaPublicacion, lenguaTraduccion, numeroPaginas, descripcion, edicion, formato, genero, copias,portada,imagen2,imagen3) VALUES(?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?)";

        try(PreparedStatement pst= db.statement(sql)) {
            pst.setString(1, book.getTitulo());
            pst.setString(2, book.getAutores());
            pst.setString(3, book.getIsbn());
            pst.setInt(4,book.getEdad());
            pst.setString(5, book.getEditorial());
            pst.setDate(6,book.getFechaEdicion());
            pst.setString(7,book.getLenguaPublicacion());
            pst.setString(8,book.getLenguaTraduccion());
            pst.setInt(9,book.getNumeroPaginas());
            pst.setString(10, book.getDescripcion());
            pst.setInt(11, book.getEdicion());
            pst.setString(12, book.getFormato());
            pst.setString(13, book.getGenero());
            pst.setInt(14, book.getCopias());
            pst.setString(15,book.getPortada());
            pst.setString(16,book.getImagen2());
            pst.setString(17,book.getImagen3());
            pst.execute();
            logger.info(BOOK_CREATED);


        } catch (SQLException throwables) {
            logger.error(throwables);
            logger.error(BOOK_NOT_CREATED);
            return HttpStatus.NOT_ACCEPTABLE;
        }
            return HttpStatus.CREATED;
    }

    /**
     *
     * {
     *         "titulo":null,
     *         "autores":null,
     *         "isbn":null,
     *         "edad":null,
     *         "editorial":null,
     *         "fechaEdicion":null,
     *         "lenguaPublicacion":null,
     *         "lenguaTraduccion":null,
     *         "numeroPaginas":null,
     *         "descripcion":null,
     *         "edicion":null,
     *         "formato":null,
     *         "genero":null,
     *         "copias":null,
     *         "portada": null,
     *         "imagen2": null,
     *         "imagen3": null
     *   }
     */

    public ArrayList <BookDTO> allBooks(String orderBy){
        String sql ="SELECT * FROM libro " + getOrder(orderBy) ;
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



    public Object readBook(int id){

        logger.info("/getBook: "+ id);
        String sql ="SELECT * FROM libro  where id =?";
        BookDTO libro=null;

        try(PreparedStatement pst= db.statement(sql)) {
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                libro= saveInBook(rs);
            }
            else{
                logger.info("BOOK '"+id+"' NOT FOUND");
                return HttpStatus.NOT_FOUND;
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return libro;
    }
}
