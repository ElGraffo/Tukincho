package com.GrupoK.Tukincho.servicios;
import com.GrupoK.Tukincho.entidades.Inmueble;
import com.GrupoK.Tukincho.enums.Provincia;
import com.GrupoK.Tukincho.repositorios.InmuebleRepositorio;
import com.GrupoK.Tukincho.entidades.Propietario;
import com.GrupoK.Tukincho.entidades.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InmuebleServicio {
    @Autowired
    InmuebleRepositorio inmuebleRepositorio;
    @Transactional
    public void crearImueble(Propietario propietario, String descripcionDelInmueble, Long precioPorNoche,
                             String otrosDetallesDelInmueble, String direccion, Provincia provincia, boolean activa, Reserva reserva) throws Exception {
        validar(descripcionDelInmueble, precioPorNoche, otrosDetallesDelInmueble, direccion, provincia);
        Inmueble inmueble = new Inmueble();
        inmueble.setPropietario(propietario);
        inmueble.setDescripcionDelInmueble(descripcionDelInmueble);
        inmueble.setPrecioPorNoche(precioPorNoche);
        inmueble.setOtrosDetallesDelInmueble(otrosDetallesDelInmueble);
        inmueble.setDireccion(direccion);
        inmueble.setProvincia(provincia);
        inmueble.setActiva(true);
        inmueble.setReserva(reserva);
        inmuebleRepositorio.save(inmueble);
    }

    @Transactional
    public void editarInmueble(String id, String descripcionDelInmueble, Long precioPorNoche,
                               String otrosDetallesDelInmueble, String direccion, Provincia provincia, Reserva reserva) throws Exception {
        validar(descripcionDelInmueble, precioPorNoche, otrosDetallesDelInmueble, direccion, provincia);
        Optional<Inmueble> inmuebleOptional = inmuebleRepositorio.findById(id);
        if (inmuebleOptional.isPresent()){
            Inmueble inmueble = inmuebleOptional.get();
            inmueble.setDescripcionDelInmueble(descripcionDelInmueble);
            inmueble.setPrecioPorNoche(precioPorNoche);
            inmueble.setOtrosDetallesDelInmueble(otrosDetallesDelInmueble);
            inmueble.setDireccion(direccion);
            inmueble.setProvincia(provincia);
            inmueble.setActiva(true);
            inmueble.setReserva(reserva);
            inmuebleRepositorio.save(inmueble);
        }
    }

    public List<Inmueble> listaDeInmuebles(){
        try{
            inmuebleRepositorio.findAll();
            return listaDeInmuebles();
        }catch (Exception e){
            return null;
        }
    }

    public Inmueble buscarInmueblePorId(String id){
        try{
            return inmuebleRepositorio.findById(id).get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Inmueble> buscarInmueblePorPrecio(Long precioPorNoche){
        try{
            return inmuebleRepositorio.buscarInmueblePorPrecio(precioPorNoche);
        }catch (Exception e){
            return null;
        }
    }

    public List<Inmueble> buscarInmueblePorProvincia(Provincia provincia){
        try{
            return inmuebleRepositorio.buscarInmueblePorProvincia(provincia);
        }catch (Exception e){
            return null;
        }
    }

    public List<Inmueble> buscarInmueblePorProvincia(String provincia){
        try{
            return inmuebleRepositorio.buscarInmueblePorProvincia(Provincia.valueOf(provincia));
        }catch (Exception e){
            return null;
        }
    }

    public List<Inmueble> buscarInmueblePorDireccion(String direccion){
        try{
            return inmuebleRepositorio.buscarInmueblePorDireccion(direccion);
        }catch (Exception e){
            return null;
        }
    }

    public void borrarInmueble(String id){
        Optional<Inmueble> inmuebleOptional = inmuebleRepositorio.findById(id);
        try{
            if(inmuebleOptional.isPresent()) inmuebleRepositorio.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void validar(String descripcionDelInmueble, Long precioPorNoche,
                         String otrosDetallesDelInmueble, String direccion, Provincia provincia) throws Exception{
        if(descripcionDelInmueble == null || descripcionDelInmueble.isEmpty())
            throw new IllegalArgumentException("La descripción del inmueble no puede estar vacia");
        if(precioPorNoche == null)
            throw new IllegalArgumentException("El precio del inmueble no puede estar vacio");
        if(otrosDetallesDelInmueble == null || otrosDetallesDelInmueble.isEmpty())
            throw new IllegalArgumentException("Los detalles extra del inmueble no pueden estar vacíos");
        if(direccion == null || direccion.isEmpty())
            throw new IllegalArgumentException("La direccion del inmueble no puede estar vacía");
        if(provincia == null)
            throw new IllegalArgumentException("La provincia del inmueble no puede estar vacía");
    }
}
