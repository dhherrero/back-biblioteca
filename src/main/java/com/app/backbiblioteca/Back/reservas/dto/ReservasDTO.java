package com.app.backbiblioteca.Back.reservas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class ReservasDTO {
    private int idLibro, idReserva;
    private String nifUsuario,estadoReserva, titulo, portada;
    private Date fechaInicio, fechaFin;


}
