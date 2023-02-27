package com.app.backbiblioteca.Back.config;

import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.zaxxer.hikari.HikariDataSource;


import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
@Service
public class DatabaseConfig {

    private final  Logger logger= LogManager.getLogger(this.getClass());
    private int contadorPool=0;

    public static HikariDataSource dataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/library-grema");
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(250);
        dataSource.setIdleTimeout(6);
        return dataSource;
    }

    public static HikariDataSource hikariDataSource = dataSource();

    public void newConeccion(){
        hikariDataSource = dataSource();
    }

    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public PreparedStatement statement (String sqlSentence) throws SQLException {
        Connection dbcon= getConnection();
        PreparedStatement pst= dbcon.prepareStatement(sqlSentence);
        //arreglillo para que no pete
        /*contadorPool+=1;
        if (9==contadorPool){
            newConeccion();
            contadorPool=0;
        }*/
        return pst;
    }





    public String prueba() {
        logger.info("Dentroo");
        String result=null;
        String sql = "SELECT  * FROM libros";
        try(PreparedStatement pst= statement(sql)){
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                result= rs.getString("titulo");
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        return result;
    }



}
