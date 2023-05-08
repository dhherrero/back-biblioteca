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
        dataSource.setMaximumPoolSize(50);

        dataSource.setMinimumIdle(2);
        dataSource.setConnectionTimeout(28000);
        dataSource.addDataSourceProperty("cachePrepStmts","true");
        dataSource.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        dataSource.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        return dataSource;
    }

    public  HikariDataSource hikariDataSource = dataSource();

    public void newConeccion(){
        hikariDataSource = dataSource();
    }

    public  Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public PreparedStatement statement (String sqlSentence) throws SQLException {
        Connection dbcon= getConnection();
        PreparedStatement pst= dbcon.prepareStatement(sqlSentence);
        //arreglillo para que no pete
        contadorPool+=1;
        if (50==contadorPool){
            newConeccion();
            contadorPool=0;
        }
        return pst;
    }







}
