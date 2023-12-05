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

@Service
public class PropietarioServicio {
    @Autowired
    PropietarioRepositorio propietarioRepositorio;
    @Autowired
    InmuebleRepositorio inmuebleRepositorio;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

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

    public Propietario buscarPropietario(String id){

        Optional<Propietario> propietarioOptional = propietarioRepositorio.findById(id);
        if(propietarioOptional.isPresent()){
            return propietarioOptional.get();
        }
        return null;
    }

    public List<Propietario> listarPropietarios(){
        try{
            return propietarioRepositorio.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public Propietario buscarPropietarioPorNombreUsuario(String nombreUsuario){
        return usuarioRepositorio.buscarUsuarioRolPropietario(nombreUsuario);
    }

    public List<Inmueble> buscarPropietarioPorInmueble(String id){
        try{
            return inmuebleRepositorio.buscarPropietarioPorInmueble(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

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
