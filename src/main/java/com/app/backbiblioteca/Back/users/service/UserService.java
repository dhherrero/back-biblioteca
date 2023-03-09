package com.app.backbiblioteca.Back.users.service;



import com.app.backbiblioteca.Back.config.DatabaseConfig;
import com.app.backbiblioteca.Back.users.controller.UserRequest;
import com.app.backbiblioteca.Back.users.controller.UserResponse;
import com.app.backbiblioteca.Back.users.userDTO.UserDTO;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
@NoArgsConstructor
public class UserService {
    private final Logger logger= LogManager.getLogger(this.getClass());
    private static DatabaseConfig db = new DatabaseConfig();

    public UserDTO saveInUser(ResultSet rs) throws SQLException {
        UserDTO user = UserDTO.builder().nif(rs.getString("nif")).nombre(rs.getString("nombre")).
                password(rs.getString("password")).fechaNacimiento(rs.getDate("fechaNacimiento")).
                telefono(rs.getInt("telefono")).direccion(rs.getString("direccion")).
                correoElectronico(rs.getString("correoElectronico")).webPersonal(rs.getString("webPersonal")).
                rol(rs.getString("rol")).build();
        return user;
    }

    public ArrayList<UserDTO> allUsers (){
        String sql ="SELECT * FROM usuario";
        ArrayList<UserDTO> listaUsers = new ArrayList<>();
        try(PreparedStatement pst= db.statement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                UserDTO user= saveInUser(rs);
                listaUsers.add(user);
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return listaUsers;
    }

    public UserResponse loginService(UserRequest userRequest){
        String sql ="SELECT nif,password,rol FROM usuario WHERE nif=?";
        logger.info("/login");
        try(PreparedStatement pst= db.statement(sql)) {
            pst.setString(1, userRequest.getNif());
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                String password =rs.getString("password");
                if (password.equals(userRequest.getPassword())){
                    logger.info("OK");
                    return UserResponse.builder().nif(rs.getString("nif")).rol(rs.getString("rol")).
                            estado(HttpStatus.OK).build();
                }
            }

        }catch (SQLException throwables) {
            logger.error(throwables);
            return UserResponse.builder().estado(HttpStatus.NOT_ACCEPTABLE).build();
        }
        logger.info("LOGIN ERROR");
        return UserResponse.builder().estado(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public HttpStatus newUser (UserDTO userDTO){
        logger.info("/newUser");
        String sql = "INSERT INTO usuario (nif, nombre, password, fechaNacimiento, telefono, direccion, correoElectronico, webPersonal, rol) VALUES(?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement pst= db.statement(sql)) {
            pst.setString(1, userDTO.getNif());pst.setString(2,userDTO.getNombre());
            pst.setString(3, userDTO.getPassword());pst.setDate(4,userDTO.getFechaNacimiento());
            pst.setInt(5, userDTO.getTelefono());pst.setString(6, userDTO.getDireccion());
            pst.setString(7, userDTO.getCorreoElectronico());pst.setString(8, userDTO.getWebPersonal());
            pst.setString(9, userDTO.getRol());
            pst.execute();
            logger.info("USER CREATED");

        }catch (SQLException throwables) {
            logger.error(throwables);
            logger.info("USER  NOT CREATED");
        return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;
    }

}
