package com.Tukincho.Tukincho.servicios;

import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.entidades.Propietario;
import com.Tukincho.Tukincho.entidades.Reserva;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.repositorios.InmuebleRepositorio;
import com.Tukincho.Tukincho.enums.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Tukincho.Tukincho.repositorios.PropietarioRepositorio;
import com.Tukincho.Tukincho.repositorios.UsuarioRepositorio;

import java.util.List;
import java.util.Optional;
/**
 * Servicio que gestiona las operaciones relacionadas con los propietarios en el sistema.
 *
 * @Service Indica que esta clase es un servicio de Spring que puede ser inyectado en otras clases.
 */
@Service
public class PropietarioServicio {
    @Autowired
    PropietarioRepositorio propietarioRepositorio;
    @Autowired
    InmuebleRepositorio inmuebleRepositorio;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    
    /**
     * Crea un nuevo propietario en el sistema a partir de un usuario existente.
     *
     * @param usuario Usuario existente que se convertirá en propietario.
     * @return Propietario creado.
     */
    public Propietario crearPropietario(Usuario usuario) {
        Propietario propietario = new Propietario();
        propietario.setNombreUsuario(usuario.getNombreUsuario());
        propietario.setNombreUsuario(usuario.getNombreUsuario());
        propietario.setEmail(usuario.getEmail());
        propietario.setPassword(usuario.getPassword());
        propietario.setActivo(usuario.getActivo());
        propietario.setRol(Rol.PROPIETARIO);
        try{
            return propietarioRepositorio.save(propietario);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Edita la información de un propietario, incluyendo la lista de inmuebles y reservas asociadas.
     *
     * @param id Identificador del propietario a editar.
     * @param inmuebles Lista de inmuebles asociados al propietario.
     * @param reserva Lista de reservas asociadas al propietario.
     */
    public void editarPropietario(String id, List<Inmueble> inmuebles, List<Reserva> reserva){
        Optional<Propietario> propietarioOptional = propietarioRepositorio.findById(id);
        if(propietarioOptional.isPresent()){
            Propietario propietario = propietarioOptional.get();
            propietario.setInmuebles(inmuebles);
            propietario.setReserva(reserva);
            try{
                propietarioRepositorio.save(propietario);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Busca un propietario por su identificador.
     *
     * @param id Identificador del propietario a buscar.
     * @return Propietario encontrado o null si no existe.
     */
    public Propietario buscarPropietario(String id){

        Optional<Propietario> propietarioOptional = propietarioRepositorio.findById(id);
        if(propietarioOptional.isPresent()){
            return propietarioOptional.get();
        }
        return null;
    }

    /**
     * Obtiene la lista de todos los propietarios registrados en el sistema.
     *
     * @return Lista de propietarios o null si ocurre un error.
     */
    public List<Propietario> listarPropietarios(){
        try{
            return propietarioRepositorio.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Busca un propietario por su nombre de usuario.
     *
     * @param nombreUsuario Nombre de usuario del propietario a buscar.
     * @return Propietario encontrado o null si no existe.
     */
    public Propietario buscarPropietarioPorNombreUsuario(String nombreUsuario){
        return usuarioRepositorio.buscarUsuarioRolPropietario(nombreUsuario);
    }
    
    /**
     * Busca la lista de inmuebles asociados a un propietario por su identificador.
     *
     * @param id Identificador del propietario.
     * @return Lista de inmuebles asociados al propietario o null si ocurre un error.
     */
    public List<Inmueble> buscarPropietarioPorInmueble(String id){
        try{
            return inmuebleRepositorio.buscarPropietarioPorInmueble(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Desactiva un propietario cambiando su rol a "Usuario" y estableciendo su estado activo a "false".
     *
     * @param id Identificador del propietario a desactivar.
     */
    public void desactivarPropietario(String id){
        Optional <Propietario> propietarioOptional = propietarioRepositorio.findById(id);
        if(propietarioOptional.isPresent()){
            Propietario propietario = propietarioOptional.get();
            propietario.setRol(Rol.USUARIO);
            propietario.setActivo(false);
            try{
                propietarioRepositorio.save(propietario);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
