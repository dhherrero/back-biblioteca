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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
@NoArgsConstructor
public class UserService {
    private final Logger logger= LogManager.getLogger(this.getClass());
    private static DatabaseConfig db = new DatabaseConfig();

    /**
     * Método encargado de guardar en un objeto tipo UserDTO el ResultSet devuelto por la base de datos
     * @param rs
     * @return el usuario
     * @throws SQLException
     */
    public UserDTO saveInUser(ResultSet rs) throws SQLException {
        UserDTO user = UserDTO.builder().nif(rs.getString("nif")).nombre(rs.getString("nombre")).
                password(rs.getString("password")).fechaNacimiento(rs.getDate("fechaNacimiento")).
                telefono(rs.getInt("telefono")).direccion(rs.getString("direccion")).
                correoElectronico(rs.getString("correoElectronico")).webPersonal(rs.getString("webPersonal")).
                rol(rs.getString("rol")).build();
        return user;
    }

    /**
     * Método para cambiar la contraseña en la base de datos
     * @param nif
     * @param newPassword
     * @return confimación del cambio de la contraseña
     * @throws SQLException
     */
    public HttpStatus changePassword (String nif, String newPassword) throws SQLException {
        String sql = "UPDATE usuario SET  password=? WHERE nif=?";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1,newPassword);
            pst.setString(2,nif);
            pst.executeUpdate();
        }catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;

    }

    /**
     * Método que borra un usuario en la base de datos
     * @param nif
     * @return confirmación de si ha borrado el usuario
     */
    public HttpStatus deleteUser(String nif){
        String sql = "DELETE FROM usuario WHERE nif=?";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1, nif);
            pst.execute();
            logger.info("USUARIO: "+ nif+ " BORRADO") ;
        } catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;
    }

    /**
     * Método que devuelve un listado de los usuarios de la base de datos
     * @return lista de usuarios
     */
    public ArrayList<UserDTO> allUsers (){
        String sql ="SELECT * FROM usuario";
        ArrayList<UserDTO> listaUsers = new ArrayList<>();
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                UserDTO user= saveInUser(rs);
                listaUsers.add(user);
            }
            dbcon.close();
            logger.info("/allUsers isClosed?: "+dbcon.isClosed());
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return listaUsers;
    }


    /**
     * Método para que devuelva la información de un usuario de la base de datos
     * @param userRequest
     * @return  usuario
     * @throws SQLException
     */
    public UserResponse infoUser(UserRequest userRequest) throws SQLException {
        String sql ="SELECT nif,nombre,password,correoElectronico, telefono, direccion FROM usuario WHERE nif=?";
        logger.info("/infoUser");
        UserResponse user;
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1, userRequest.getNif());
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                user= UserResponse.builder().nif(rs.getString("nif")).
                        nombre(rs.getNString("nombre")).
                        password(rs.getString("password")).
                        correoElectronico(rs.getString("correoElectronico")).
                        telefono(rs.getInt("telefono")).
                        direccion(rs.getString("direccion")).
                        estado(HttpStatus.OK).build();
                dbcon.close();
                logger.info("/infoUsers isClosed?: "+dbcon.isClosed());
                return user;
            }

        }
        catch (SQLException throwables) {
            logger.error(throwables);

            return UserResponse.builder().estado(HttpStatus.NOT_ACCEPTABLE).build();
        }
        logger.info("LOGIN ERROR");
        return UserResponse.builder().estado(HttpStatus.NOT_ACCEPTABLE).build();
    }

    /**
     * Método encargado de realizar la lógica del login
     * @param userRequest
     * @return confirmación de si se ha logueado
     */
    public UserResponse loginService(UserRequest userRequest){
        String sql ="SELECT nif,password,rol FROM usuario WHERE nif=?";
        logger.info("/login");
        UserResponse user;
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1, userRequest.getNif());
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                String password =rs.getString("password");
                if (password.equals(userRequest.getPassword())){
                    logger.info("OK");
                    user= UserResponse.builder().nif(rs.getString("nif")).rol(rs.getString("rol")).
                            estado(HttpStatus.OK).build();
                    dbcon.close();
                    logger.info("/login isClosed?: "+dbcon.isClosed());
                    return user;
                }
                dbcon.close();
                logger.info("/login isClosed?: "+dbcon.isClosed());
            }

        }catch (SQLException throwables) {
            logger.error(throwables);
            return UserResponse.builder().estado(HttpStatus.NOT_ACCEPTABLE).build();
        }
        logger.info("LOGIN ERROR");
        return UserResponse.builder().estado(HttpStatus.NOT_ACCEPTABLE).build();
    }

    /**
     * Introduce un nuevo usuario en la base de datos
     * @param userDTO
     * @return confirmación si se ha introducido el usuario en la base de datos
     */
    public HttpStatus newUser (UserDTO userDTO){
        logger.info("/newUser");
        String sql = "INSERT INTO usuario (nif, nombre, password, fechaNacimiento, telefono, direccion, correoElectronico, webPersonal, rol) VALUES(?,?,?,?,?,?,?,?,?)";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1, userDTO.getNif());pst.setString(2,userDTO.getNombre());
            pst.setString(3, userDTO.getPassword());pst.setDate(4,userDTO.getFechaNacimiento());
            pst.setInt(5, userDTO.getTelefono());pst.setString(6, userDTO.getDireccion());
            pst.setString(7, userDTO.getCorreoElectronico());pst.setString(8, userDTO.getWebPersonal());
            pst.setString(9, userDTO.getRol());
            pst.execute();
            logger.info("USER CREATED");
            dbcon.close();
            logger.info("/newUser isClosed?2: "+dbcon.isClosed());

        }catch (SQLException throwables) {
            logger.error(throwables);
            logger.info("USER  NOT CREATED");
        return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;
    }

    /**
     * Actualiza un usuario en la base de datos
     * @param userDTO
     * @return confirmación de si se ha realizado correctamente
     */
    public HttpStatus updateUser (UserDTO userDTO){
        logger.info("/updateUser");
        String sql="UPDATE usuario SET nombre=?, password=?, correoElectronico=?, telefono=?, direccion=?, webPersonal=? WHERE nif=?";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(7, userDTO.getNif());pst.setString(1,userDTO.getNombre());
            pst.setString(2, userDTO.getPassword());
            pst.setInt(4, userDTO.getTelefono());pst.setString(5, userDTO.getDireccion());
            pst.setString(3, userDTO.getCorreoElectronico());pst.setString(6, userDTO.getWebPersonal());
            pst.executeUpdate();
            logger.info("USER updated");
            dbcon.close();
        }catch (SQLException throwables) {
            logger.error(throwables);
            logger.info("USER  NOT UPDATED");
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;

    }

}
