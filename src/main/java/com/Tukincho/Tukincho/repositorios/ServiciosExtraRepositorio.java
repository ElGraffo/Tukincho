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
@Repository
public interface ServiciosExtraRepositorio extends JpaRepository<ServiciosExtra, String> {
    
}
