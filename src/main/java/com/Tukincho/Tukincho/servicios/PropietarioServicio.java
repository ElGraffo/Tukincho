<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/servicios/PropietarioServicio.java
package com.GrupoK.Tukincho.servicios;
import com.GrupoK.Tukincho.entidades.Inmueble;
import com.GrupoK.Tukincho.entidades.Propietario;
import com.GrupoK.Tukincho.entidades.Reserva;
import com.GrupoK.Tukincho.entidades.Usuario;
import com.GrupoK.Tukincho.enums.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.GrupoK.Tukincho.repositorios.InmuebleRepositorio;
import com.GrupoK.Tukincho.repositorios.PropietarioRepositorio;
=======
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
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/servicios/PropietarioServicio.java

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioServicio {
    @Autowired
    PropietarioRepositorio propietarioRepositorio;
    @Autowired
    InmuebleRepositorio inmuebleRepositorio;

    public Propietario crearPropietario(Usuario usuario) {
        Propietario propietario = new Propietario();
<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/servicios/PropietarioServicio.java
        propietario.setNombreUsuario(usuario.getNombreUsuario());
=======
        propietario.setNombre(usuario.getNombre());
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/servicios/PropietarioServicio.java
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

<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/servicios/PropietarioServicio.java
    public void editarPropietario(String id,Inmueble inmueble, Reserva reserva){
        Optional<Propietario> propietarioOptional = propietarioRepositorio.findById(id);
        if(propietarioOptional.isPresent()){
            Propietario propietario = propietarioOptional.get();
            propietario.setInmueble(inmueble);
=======
    public void editarPropietario(String id, List<Inmueble> inmuebles, Reserva reserva){
        Optional<Propietario> propietarioOptional = propietarioRepositorio.findById(id);
        if(propietarioOptional.isPresent()){
            Propietario propietario = propietarioOptional.get();
            propietario.setInmuebles(inmuebles);
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/servicios/PropietarioServicio.java
            propietario.setReserva(reserva);
            try{
                propietarioRepositorio.save(propietario);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/servicios/PropietarioServicio.java
    public Propietario encontrarPropietario(String id){
=======
    public Propietario buscarPropietario(String id){
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/servicios/PropietarioServicio.java
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

<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/servicios/PropietarioServicio.java
    public List<Propietario> listarPropietarioPorInmueble(String id){
=======
    public List<Inmueble> buscarPropietarioPorInmueble(String id){
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/servicios/PropietarioServicio.java
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
<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/servicios/PropietarioServicio.java

=======
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/servicios/PropietarioServicio.java
}
