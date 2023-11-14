package com.GrupoK.Tukincho.repositorios;
import com.GrupoK.Tukincho.entidades.Inmueble;
import com.GrupoK.Tukincho.entidades.Propietario;
import com.GrupoK.Tukincho.enums.Provincia;
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

<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/repositorios/InmuebleRepositorio.java
    @Query("SELECT p FROM Propietario p where p.inmueble.id = :id") // chequear que esté bien
    List<Propietario> buscarPropietarioPorInmueble(String id);
=======
    @Query("SELECT p.inmuebles FROM Propietario p WHERE p.id = :propietarioId")
    List<Inmueble> buscarPropietarioPorInmueble(@Param("propietarioId") String propietarioId);

    // todo -> hacer query de buscar por localidad
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/repositorios/InmuebleRepositorio.java
}

