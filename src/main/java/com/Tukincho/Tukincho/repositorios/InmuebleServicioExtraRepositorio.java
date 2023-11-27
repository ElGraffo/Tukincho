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
@Repository
public interface InmuebleServicioExtraRepositorio extends JpaRepository<InmuebleServicioExtra, String>{
    
}
