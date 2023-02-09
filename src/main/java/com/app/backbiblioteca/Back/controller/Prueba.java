package com.app.backbiblioteca.Back.controller;



import com.app.backbiblioteca.Back.dto.BookDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.util.ArrayList;


@RestController
public class Prueba {

    private final Logger logger= LogManager.getLogger(this.getClass());

    @GetMapping("/hello")
    public ArrayList<Response> sayHello(){
        logger.info("/hello");
        ArrayList <Response> lista= new ArrayList<>();
        Response response = new Response();
        response.setName("Holaaa");
        response.setApellido("Sii");
        Response response2 = new Response();
        response2.setName("Holaaa");
        response2.setApellido("Sii");
        lista.add(response);
        lista.add(response2);

        return lista;
    }

    @GetMapping("/allBooks")
    public ArrayList<BookDTO> getLibros(){
        logger.info("/allBooks");
        BookDTO libro1 = new BookDTO(1,101,2,256,4,"La vida es bella",
                "https://images.cdn2.buscalibre.com/fit-in/360x360/28/e1/28e1f4a3ba4cfc7422dda5c3f96cd041.jpg",
                "ISBN","Javier","Planeta de libros","02/02/2002",
                "Inglés","Español","Bueno","Drama");
        BookDTO libro2 = new BookDTO(2,102,57,26786,4,"La vida es mala",
                "https://edit.org/images/cat/portadas-libros-big-2019101610.jpg",
                "ISBN","Roberto","Venus","04/08/2012",
                "Inglés","Español","Bueno","Terror");
        ArrayList <BookDTO> lista = new ArrayList<>();
        lista.add(libro1);
        lista.add(libro2);
        return lista;
    }
}
