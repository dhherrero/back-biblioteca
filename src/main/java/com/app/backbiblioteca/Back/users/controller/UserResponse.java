package com.app.backbiblioteca.Back.users.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@Builder
public class UserResponse {
    String nif,rol;
    HttpStatus estado;
}
