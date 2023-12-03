package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.ServiciosExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonatan Atencio
 * @version 1.0
 * @Data 18/11/2023
 */

/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad ServiciosExtra.
 * Utiliza Spring Data JPA y extiende de JpaRepository para proporcionar métodos de acceso a datos comunes.
 */
@Repository
public interface ServiciosExtraRepositorio extends JpaRepository<ServiciosExtra, String> {
    
}
