package com.app.backbiblioteca.Back.users.controller;

import com.app.backbiblioteca.Back.users.service.UserService;
import com.app.backbiblioteca.Back.users.userDTO.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger= LogManager.getLogger(this.getClass());
    UserService userService= new UserService();
    private static final String USER_CREATED= "USER CREATED";
    private static final String NOT_SUCCESFUL= "USER NOT CREATED";

    public ResponseEntity<?> getResponse(HttpStatus httpStatus){
        Map<HttpStatus,ResponseEntity<?>> mapResponse= new HashMap<>();
        mapResponse.put(HttpStatus.OK,new ResponseEntity<>("OK",HttpStatus.OK));
        mapResponse.put(HttpStatus.CREATED,new ResponseEntity<>(USER_CREATED,HttpStatus.CREATED));
        mapResponse.put(HttpStatus.NOT_ACCEPTABLE,new ResponseEntity<>(NOT_SUCCESFUL,HttpStatus.NOT_ACCEPTABLE));
        return mapResponse.get(httpStatus);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?>  getUsers(){
        ArrayList<UserDTO> response = userService.allUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("infoUser")
    public UserResponse infoUser(@RequestBody UserRequest payload) throws SQLException {
        logger.info(payload.toString());
        return userService.infoUser(payload);
    }

    @PostMapping("login")
    public UserResponse login (@RequestBody UserRequest payload){
        logger.info(payload.toString());
        return userService.loginService(payload);
    }

    @PostMapping("changePassword")
    public ResponseEntity<?> changePassword (@RequestBody UserRequest payload) throws SQLException {
        HttpStatus httpStatus = userService.changePassword(payload.getNif(), payload.getPassword());
        return getResponse(httpStatus);
    }

    @PostMapping("deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody UserRequest payload) throws SQLException {
        HttpStatus httpStatus = userService.deleteUser(payload.getNif());
        return getResponse(httpStatus);
    }

    @PostMapping("newUser")
    public ResponseEntity<?>  newUser(@RequestBody UserRequest payload){
        UserDTO user = UserDTO.builder().nif(payload.getNif()).nombre(payload.getNombre()).password(payload.getPassword()).
                fechaNacimiento(new Date(payload.getFechaNacimiento())).telefono(payload.getTelefono()).direccion(payload.getDireccion()).
                correoElectronico(payload.getCorreoElectronico()).webPersonal(payload.getWebPersonal()).rol(payload.getRol()).build();
        HttpStatus httpStatus = userService.newUser(user);

        return getResponse(httpStatus);
    }



}
