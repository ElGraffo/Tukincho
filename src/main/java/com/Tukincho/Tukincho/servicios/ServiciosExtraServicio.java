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

/**
 * Servicio que gestiona las operaciones relacionadas con los servicios extra en el sistema.
 *
 * @Service Indica que esta clase es un servicio de Spring que puede ser inyectado en otras clases.
 */
@Service
public class ServiciosExtraServicio {
    
    @Autowired
    private ServiciosExtraRepositorio serviciosExtraRepositorio;
    
    /**
     * Crea y guarda un nuevo servicio extra en el sistema.
     *
     * @param nombre Nombre del servicio extra a crear.
     * @return Servicio extra creado y almacenado en la base de datos.
     * @throws Exception Si el nombre del servicio es nulo o vacío.
     */
    public ServiciosExtra crear(String nombre) throws Exception{
        ServiciosExtra servicio = new ServiciosExtra();
        validar(nombre);
        servicio.setNombreDelServicioExtra(nombre);
        return serviciosExtraRepositorio.save(servicio);
    }
    
    /**
     * Crea y guarda un nuevo servicio extra en el sistema.
     *
     * @param servicio Servicio extra a crear y almacenar.
     * @return Servicio extra creado y almacenado en la base de datos.
     * @throws Exception Si el nombre del servicio en el objeto proporcionado es nulo o vacío.
     */
     public ServiciosExtra crear(ServiciosExtra servicio) throws Exception{
         validar(servicio);
         return serviciosExtraRepositorio.save(servicio);
     }
    
    /**
     * Valida que el nombre del servicio no sea nulo o vacío.
     *
     * @param nombre Nombre del servicio a validar.
     * @throws Exception Si el nombre del servicio es nulo o vacío.
     */
    private void validar(String nombre) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre del servicio no puede ser vacio");
        }
    }
    
    /**
     * Valida que el nombre del servicio en el objeto proporcionado no sea nulo o vacío.
     *
     * @param servicio Servicio extra a validar.
     * @throws Exception Si el nombre del servicio en el objeto proporcionado es nulo o vacío.
     */
        private void validar(ServiciosExtra servicio) throws Exception{
        if(servicio.getNombreDelServicioExtra() == null || servicio.getNombreDelServicioExtra().isEmpty()){
            throw new Exception("El nombre del servicio no puede ser vacio");
        }
        
    }
}
