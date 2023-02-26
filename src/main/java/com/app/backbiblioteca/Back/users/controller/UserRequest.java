package com.app.backbiblioteca.Back.users.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class UserRequest {
    String nif, nombre, password,direccion, correoElectronico, webPersonal,rol, estadoUsuario;
    int telefono;
    Date fechaNacimiento;

    @Override
    public String toString() {
        return "UserRequest{" +
                "nif='" + nif + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
