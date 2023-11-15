
<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/repositorios/ReservaRepositorio.java
package com.GrupoK.Tukincho.repositorios;

import com.GrupoK.Tukincho.entidades.Reserva;
=======
package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Reserva;

>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/repositorios/ReservaRepositorio.java
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexi
 */

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, String> {
    //buscar reserva por el id de un usuario
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :id")
    List<Reserva> findByUsuarioId(@Param("id") String id);
    
    @Query("SELECT r FROM Reserva r WHERE r.fechaInicioReserva = :fechaInicio")
    List<Reserva> findByFechaInicioReserva(@Param("fechaInicio") Date fechaInicio);

    // Método para buscar reservas por fecha de fin
    @Query("SELECT r FROM Reserva r WHERE r.fechaFinReserva = :fechaFin")
    List<Reserva> findByFechaFinReserva(@Param("fechaFin") Date fechaFin);

   // Método para buscar reservas por el ID de un inmueble
    @Query("SELECT r FROM Reserva r WHERE r.inmueble.id = :inmuebleId")
    List<Reserva> findByInmuebleId(@Param("inmuebleId") String inmuebleId);

    // Método para verificar si hay reservas para un inmueble en una fecha específica
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.inmueble.id = :inmuebleId AND :fecha BETWEEN r.fechaInicioReserva AND r.fechaFinReserva")
    boolean existeReservaEnFecha(@Param("inmuebleId") String inmuebleId, @Param("fecha") Date fecha);

    @Modifying
    @Transactional
    @Query("UPDATE Reserva r SET r.activo = :activo WHERE r.id = :reservaId")
    void cambiarEstadoReserva(@Param("reservaId") String reservaId, @Param("activo") Boolean activo);
    
    @Query("SELECT r FROM Reserva r")
    List<Reserva> findAllReservas();

    // Método para buscar todas las reservas activas
    @Query("SELECT r FROM Reserva r WHERE r.activo = true")
    List<Reserva> findAllReservasActivas();
    
    // Método para buscar todas las reservas inactivas
    @Query("SELECT r FROM Reserva r WHERE r.activo = false")
    List<Reserva> findAllReservasInactivas();

}
