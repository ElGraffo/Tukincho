package com.Tukincho.Tukincho.servicios;

import com.Tukincho.Tukincho.entidades.ServiciosExtra;
import com.Tukincho.Tukincho.repositorios.ServiciosExtraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonatan Atencio
 * @version 1.0
 * @data 18/11/2023
 */
@Service
public class ServiciosExtraServicio {
    
    @Autowired
    private ServiciosExtraRepositorio serviciosExtraRepositorio;
    
    
    public ServiciosExtra crear(String nombre) throws Exception{
        ServiciosExtra servicio = new ServiciosExtra();
        validar(nombre);
        servicio.setNombreDelServicioExtra(nombre);
        return serviciosExtraRepositorio.save(servicio);
    }
    
     public ServiciosExtra crear(ServiciosExtra servicio) throws Exception{
         validar(servicio);
         return serviciosExtraRepositorio.save(servicio);
     }
    
    private void validar(String nombre) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre del servicio no puede ser vacio");
        }
    }
        
        private void validar(ServiciosExtra servicio) throws Exception{
        if(servicio.getNombreDelServicioExtra() == null || servicio.getNombreDelServicioExtra().isEmpty()){
            throw new Exception("El nombre del servicio no puede ser vacio");
        }
        
    }
}
