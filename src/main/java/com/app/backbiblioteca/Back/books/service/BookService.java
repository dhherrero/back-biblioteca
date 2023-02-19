package com.app.backbiblioteca.Back.books.service;

import com.app.backbiblioteca.Back.config.DatabaseConfig;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


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
}
