package com.Tukincho.Tukincho.repositorios;
import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.enums.Provincia;
import com.Tukincho.Tukincho.entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InmuebleRepositorio extends JpaRepository<Inmueble, String> {

    @Query("SELECT i FROM Inmueble i where i.precioPorNoche = :precioPorNoche")
    public List<Inmueble> buscarInmueblePorPrecio(@Param("precioPorNoche") Long precioPorNoche);

    @Query("SELECT i FROM Inmueble i where i.provincia = :provincia")
    public List<Inmueble> buscarInmueblePorProvincia(@Param("provincia") Provincia provincia);

    @Query("SELECT i FROM Inmueble i where i.direccion LIKE CONCAT('%',:direccion,'%')")
    public List<Inmueble> buscarInmueblePorDireccion(@Param("direccion") String direccion);

    @Query("SELECT p.inmuebles FROM Propietario p WHERE p.id = :propietarioId")
    List<Inmueble> buscarPropietarioPorInmueble(@Param("propietarioId") String propietarioId);


    @Query(value = "SELECT i.* FROM Inmueble i "
            + "LEFT JOIN Feedback f ON i.id = f.inmueble_id "
            + "GROUP BY i.id "
            + "ORDER BY COALESCE(AVG(f.calificacion), 0) DESC "
            + "LIMIT :pageSize OFFSET :pageNumber", nativeQuery = true)
    List<Inmueble> obtenerTopInmueblesPorCalificacion(@Param("pageSize") int pageSize, @Param("pageNumber") int pageNumber);



    // todo -> hacer query de buscar por localidad



    @Query("SELECT i FROM Inmueble i " +
            "WHERE (:provincia IS NULL OR i.provincia = :provincia) " +
            "AND NOT EXISTS (" +
            "  SELECT r FROM Reserva r " +
            "  WHERE r.inmueble = i " +
            "  AND (:fechaEntrada IS NOT NULL AND :fechaEntrada BETWEEN r.fechaInicioReserva AND r.fechaFinReserva) " +
            "  OR (:fechaSalida IS NOT NULL AND :fechaSalida BETWEEN r.fechaInicioReserva AND r.fechaFinReserva)" +
            ")")
    List<Inmueble> buscarInmueblesDisponibles(@Param("provincia") Provincia provincia,
                                              @Param("fechaEntrada") Date fechaEntrada,
                                              @Param("fechaSalida") Date fechaSalida);
}

