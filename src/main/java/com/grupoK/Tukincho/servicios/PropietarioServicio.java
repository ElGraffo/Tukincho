package com.grupoK.Tukincho.servicios;

import com.grupoK.Tukincho.entidades.Inmueble;
import com.grupoK.Tukincho.entidades.Propietario;
import com.grupoK.Tukincho.entidades.Reserva;
import com.grupoK.Tukincho.entidades.Usuario;
import com.grupoK.Tukincho.enums.Rol;
import com.grupoK.Tukincho.repositorios.InmuebleRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupoK.Tukincho.repositorios.PropietarioRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioServicio {
    @Autowired
    PropietarioRepositorio propietarioRepositorio;
    @Autowired
    InmuebleRepositorio inmuebleRepositorio;

    public void crearPropietario(Usuario usuario) {
        Propietario propietario = new Propietario();
        propietario.setNombre(usuario.getNombre());
        propietario.setEmail(usuario.getEmail());
        propietario.setPassword(usuario.getPassword());
        propietario.setActivo(usuario.getActivo());
        propietario.setRol(Rol.PROPIETARIO);
        try{
            propietarioRepositorio.save(propietario);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editarPropietario(String id, List<Inmueble> inmuebles, Reserva reserva){
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
