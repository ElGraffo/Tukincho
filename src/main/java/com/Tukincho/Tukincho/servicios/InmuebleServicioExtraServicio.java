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
@Service
public class InmuebleServicioExtraServicio {
    @Autowired
    private InmuebleServicioExtraRepositorio inmuebleServicioExtraRepositorio;

    public void guardarInmuebleServicioExtra(InmuebleServicioExtra inmuebleServicioExtra) {
        inmuebleServicioExtraRepositorio.save(inmuebleServicioExtra);
    }

    public List<InmuebleServicioExtra> obtenerTodosLosInmuebleServicioExtra() {
        return inmuebleServicioExtraRepositorio.findAll();
    }
}
