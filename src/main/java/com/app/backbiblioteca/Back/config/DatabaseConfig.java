package com.app.backbiblioteca.Back.config;

import com.zaxxer.hikari.HikariDataSource;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Service
public class DatabaseConfig {

    private final  Logger logger= LogManager.getLogger(this.getClass());




    public static HikariDataSource dataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/library-grema");
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }
    public static Connection getConnection() throws SQLException {
        return dataSource().getConnection();
    }


    public String prueba() {
        logger.info("Dentroo");
        String result=null;
        String sql = "SELECT  * FROM libros";
        try(Connection dbcon= getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)){
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
