package com.app.backbiblioteca.Back.Biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.app.backbiblioteca.Back.controller"})
public class BackBibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackBibliotecaApplication.class, args);
	}

}
