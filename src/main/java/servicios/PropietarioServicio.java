package servicios;

import entidades.Inmueble;
import entidades.Propietario;
import entidades.Reserva;
import entidades.Usuario;
import enums.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.InmuebleRepositorio;
import repositorios.PropietarioRepositorio;

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

    public void editarPropietario(String id,Inmueble inmueble, Reserva reserva){
        Optional<Propietario> propietarioOptional = propietarioRepositorio.findById(id);
        if(propietarioOptional.isPresent()){
            Propietario propietario = propietarioOptional.get();
            propietario.setInmueble(inmueble);
            propietario.setReserva(reserva);
            try{
                propietarioRepositorio.save(propietario);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Propietario encontrarPropietario(String id){
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

    public List<Propietario> listarPropietarioPorInmueble(String id){
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
