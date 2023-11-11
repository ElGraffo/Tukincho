package com.Tukincho.Tukincho.repositorios;

import com.grupoK.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.enums.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InmuebleRepositorio extends JpaRepository<Inmueble, String> {

    @Query("SELECT i FROM Inmueble i where i.precioPorNoche = :precioPorNoche")
    public List<Inmueble> buscarInmueblePorPrecio(@Param("precioPorNoche") Long precioPorNoche);

    @Query("SELECT i FROM Inmueble i where i.provincia = :provincia")
    public List<Inmueble> buscarInmueblePorProvincia(@Param("provincia") Provincia provincia);

    @Query("SELECT i FROM Inmueble i where i.direccion LIKE CONCAT('%',:direccion,'%')")
    public List<Inmueble> buscarInmueblePorDireccion(@Param("direccion") String direccion);
}
