
package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Propietario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepositorio extends JpaRepository<Propietario, String> {
      @Query("select p from Propietario p where p.nombreUsuario = :nombreUsuario")
    List< Propietario> buscarPorPropietario(@Param("nombreUsuario") String nombreUsuario);

}
