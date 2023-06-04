package com.app.backbiblioteca.Back.reservas.controller;


import com.app.backbiblioteca.Back.reservas.dto.ReservasDTO;
import com.app.backbiblioteca.Back.reservas.service.ReservaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/reserva")
public class ReservaController {
    /**
     * Obtiene un objeto e tipo ResponseEntity en función del httpstatus introducido
     * @param httpStatus
     * @return Objeto tipo ResponseEntity
     */
    public ResponseEntity<?> getResponse(HttpStatus httpStatus){
        Map<HttpStatus,ResponseEntity<?>> mapResponse= new HashMap<>();
        mapResponse.put(HttpStatus.OK,new ResponseEntity<>("OK",HttpStatus.OK));
        mapResponse.put(HttpStatus.NOT_ACCEPTABLE,new ResponseEntity<>("NOT_SUCCESFUL",HttpStatus.NOT_ACCEPTABLE));
        return mapResponse.get(httpStatus);
    }
    private static   ReservaService reservaService = new ReservaService();
    private final Logger logger= LogManager.getLogger(this.getClass());


    /**
     * Endpoint encargado de realizar una nueva reserva
     * @param reservasDTO
     * @return confirmación de si la operación se ha realizado con éxito
     * @throws SQLException
     */
    @PostMapping("/new")
    public ResponseEntity <?> newReserva(@RequestBody ReservasDTO reservasDTO) throws SQLException {
        logger.info("idlibroo: "+ reservasDTO.getIdLibro());
        HttpStatus httpStatus = reservaService.newReserva(reservasDTO);
        String mensaje = "NOT ACCEPTABLE";
        if (httpStatus.equals(HttpStatus.CREATED)){
            mensaje= "CREATED";
        }
        return new ResponseEntity<>(mensaje,httpStatus);
    }

    /**
     * Endpoint encaargado de devolver todas las reservas
     * @return listado de reservas
     */
    @PostMapping("/all")
    public ResponseEntity <?> allReservas(){
        return new ResponseEntity(reservaService.allReservas(), HttpStatus.OK);
    }

    /**
     * Endpoint encargado de devlverlas reservas activas de un usuario
     * @param reservasDTO
     * @return listado de reservas
     */
    @PostMapping("/reservasDe")
    public ResponseEntity <?> reservasDe(@RequestBody ReservasDTO reservasDTO){
        return new ResponseEntity(reservaService.nifReservas(reservasDTO.getNifUsuario()), HttpStatus.OK);
    }

    /**
     * Endpoint encargado de cancelar una reserva
     * @param reservasDTO
     * @return confirmación de que la reserva se ha cancelado
     */
    @PostMapping("cancelarReserva")
    public ResponseEntity <?> cancelar(@RequestBody ReservasDTO reservasDTO){
        return getResponse(reservaService.cancelarRservas(reservasDTO.getIdReserva()));
    }

    /**
     * Endpoint encargado de ampliar una reserva
     * @param reservasDTO
     * @return confirmación de que la reserva se ha ampliado con éxito
     */
    @PostMapping("ampliarReserva")
    public ResponseEntity <?> ampliar(@RequestBody ReservasDTO reservasDTO){
        return getResponse(reservaService.ampliarReserva(reservasDTO.getIdReserva()));
    }



}
