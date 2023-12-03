package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.InmuebleServicioExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonatan Atencio
 * @version 
 * @data 18/11/2023
 */

/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad InmuebleServicioExtra.
 * Utiliza Spring Data JPA y extiende de JpaRepository para proporcionar métodos de acceso a datos comunes.
 */
@Repository
public interface InmuebleServicioExtraRepositorio extends JpaRepository<InmuebleServicioExtra, String>{
    
}
