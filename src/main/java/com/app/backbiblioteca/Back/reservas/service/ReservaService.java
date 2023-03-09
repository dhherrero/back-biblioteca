package com.app.backbiblioteca.Back.reservas.service;

import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.config.DatabaseConfig;
import com.app.backbiblioteca.Back.reservas.dto.ReservasDTO;
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
public class ReservaService {
    private final Logger logger= LogManager.getLogger(this.getClass());
    private static DatabaseConfig db = new DatabaseConfig();

    public HttpStatus newReserva(ReservasDTO reservasDTO){
        String sql = "INSERT INTO reservas (nifUsuario, idLibro, fechaInicio, fechaFin, estadoReserva) VALUES(?, ?, ?, ?, ?)";

        try(PreparedStatement pst= db.statement(sql)) {
            pst.setString(1,reservasDTO.getNifUsuario());

            pst.setInt(2,reservasDTO.getIdLibro());
            pst.setDate(3, reservasDTO.getFechaInicio());
            pst.setDate(4,reservasDTO.getFechaFin());
            pst.setString(5, "activa");
            pst.execute();
        }
        catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.CREATED;
    }
    public ReservasDTO saveInReserva(ResultSet rs) throws SQLException {
        return ReservasDTO.builder().idReserva(rs.getInt("id")).idLibro(rs.getInt("idLibro")).fechaFin(rs.getDate("fechaFin")).fechaInicio(rs.getDate("fechaInicio")).nifUsuario(rs.getString("nifUsuario")).estadoReserva(rs.getString("estadoReserva")).build();
    }

    public ArrayList<ReservasDTO> allReservas(){
        String sql ="SELECT * FROM reservas";
        ArrayList <ReservasDTO> listaReservas = new ArrayList<>();
        try(PreparedStatement pst= db.statement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                ReservasDTO reserva= saveInReserva(rs);
                listaReservas.add(reserva);
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return listaReservas;
    }

    public ArrayList<ReservasDTO> nifReservas(String nifUsuario){
        String sql ="SELECT * FROM reservas WHERE nifUsuario='"+ nifUsuario +"'";
        ArrayList <ReservasDTO> listaReservas = new ArrayList<>();
        try(PreparedStatement pst= db.statement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                ReservasDTO reserva= saveInReserva(rs);
                listaReservas.add(reserva);
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return listaReservas;
    }
}
