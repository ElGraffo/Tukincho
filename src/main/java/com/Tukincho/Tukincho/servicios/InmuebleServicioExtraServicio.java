package com.Tukincho.Tukincho.servicios;

import com.Tukincho.Tukincho.entidades.InmuebleServicioExtra;
import com.Tukincho.Tukincho.repositorios.InmuebleServicioExtraRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonatan Atencio
 * @version 1.0
 * @data 18/11/2023
 */

/**
 * Servicio que gestiona las operaciones relacionadas con los servicios extras asociados a los inmuebles en el sistema.
 *
 * @Service Indica que esta clase es un servicio de Spring que puede ser inyectado en otras clases.
 */

@Service
public class InmuebleServicioExtraServicio {
    @Autowired
    private InmuebleServicioExtraRepositorio inmuebleServicioExtraRepositorio;
    
    /**
     * Guarda un nuevo servicio extra asociado a un inmueble en la base de datos.
     *
     * @param inmuebleServicioExtra Objeto que representa el servicio extra asociado a un inmueble.
     */
    public void guardarInmuebleServicioExtra(InmuebleServicioExtra inmuebleServicioExtra) {
        inmuebleServicioExtraRepositorio.save(inmuebleServicioExtra);
    }

    /**
     * Obtiene todos los servicios extras asociados a los inmuebles almacenados en la base de datos.
     *
     * @return Lista de todos los servicios extras asociados a los inmuebles.
     */
    public List<InmuebleServicioExtra> obtenerTodosLosInmuebleServicioExtra() {
        return inmuebleServicioExtraRepositorio.findAll();
    }
}
