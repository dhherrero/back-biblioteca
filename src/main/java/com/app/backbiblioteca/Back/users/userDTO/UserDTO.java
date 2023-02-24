package com.app.backbiblioteca.Back.users.userDTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class UserDTO {
    String nif, nombre, password,direccion, correoElectronico, webPersonal,rol, estadoUsuario;
    int telefono;
    Date fechaNacimiento;
}
