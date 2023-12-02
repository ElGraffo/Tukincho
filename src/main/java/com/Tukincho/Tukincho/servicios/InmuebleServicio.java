package com.Tukincho.Tukincho.servicios;

import com.Tukincho.Tukincho.entidades.Imagen;
import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.entidades.InmuebleServicioExtra;
import com.Tukincho.Tukincho.entidades.Propietario;
import com.Tukincho.Tukincho.entidades.Reserva;
import com.Tukincho.Tukincho.enums.Provincia;
import com.Tukincho.Tukincho.repositorios.InmuebleRepositorio;
import com.Tukincho.Tukincho.repositorios.ServiciosExtraRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.stereotype.Service;

/**
 * Servicio que gestiona las operaciones relacionadas con los inmuebles en el sistema.
 *
 * @Service Indica que esta clase es un servicio de Spring que puede ser inyectado en otras clases.
 */

@Service
public class InmuebleServicio {

    @Autowired
    InmuebleRepositorio inmuebleRepositorio;
    @Autowired
    ImagenServicio imagenServicio;
    @Autowired
    ServiciosExtraRepositorio serviciosExtraRepositorio;

    /**
     * Crea un nuevo inmueble con la información proporcionada, incluyendo imágenes y servicios extras.
     *
     * @param propietario Propietario del inmueble.
     * @param inmueble Objeto que representa el inmueble a crear.
     * @param nombre Nombre del inmueble.
     * @param descripcionDelInmueble Descripción del inmueble.
     * @param precioPorNoche Precio por noche del inmueble.
     * @param otrosDetallesDelInmueble Otros detalles del inmueble.
     * @param direccion Dirección del inmueble.
     * @param provincia Provincia del inmueble.
     * @param reserva Lista de reservas asociadas al inmueble.
     * @param imagenes Lista de archivos de imágenes asociadas al inmueble.
     * @param serviciosExtras Lista de servicios extras asociados al inmueble.
     * @throws Exception Si ocurre un error durante la operación.
     */
    @Transactional
    public void crearInmueble(Propietario propietario,Inmueble inmueble,
            String nombre, 
            String descripcionDelInmueble, 
            Long precioPorNoche,
                              String otrosDetallesDelInmueble, 
                              String direccion, Provincia provincia,
                              List<Reserva> reserva, 
                              List<MultipartFile> imagenes,
                              List<InmuebleServicioExtra> serviciosExtras) throws Exception {
        
        validar(nombre,descripcionDelInmueble, precioPorNoche, otrosDetallesDelInmueble, direccion, provincia);
        inmueble.setPropietario(propietario);
        inmueble.setNombre(nombre);
        inmueble.setDescripcionDelInmueble(descripcionDelInmueble);
        inmueble.setPrecioPorNoche(precioPorNoche);
        inmueble.setOtrosDetallesDelInmueble(otrosDetallesDelInmueble);
        inmueble.setDireccion(direccion);
        inmueble.setProvincia(provincia);
        inmueble.setActiva(true);
        inmueble.setReservas(reserva);
        inmueble.setInmuebleServiciosExtras(serviciosExtras);
        inmueble = inmuebleRepositorio.save(inmueble);
       
        // guardar las imágenes.
        List<Imagen> imagenesGuardadas = new ArrayList<>();
        for (MultipartFile imagen : imagenes) {
            //guardo la imagen y la retorna con su id
            Imagen imagenGuardada = imagenServicio.guardar(imagen);
            imagenGuardada.setInmueble(inmueble);//le asigno a quien pertenese esa imagen
            imagenesGuardadas.add(imagenGuardada);
        }

        // Asignar las imágenes al inmueble
        inmueble.setImagen(imagenesGuardadas);

        // Guardar el inmueble en la base de datos
        inmuebleRepositorio.save(inmueble);
    }

    
    /**
     * Edita un inmueble existente con la información proporcionada, incluyendo imágenes y servicios extras.
     *
     * @param id Identificador único del inmueble a editar.
     * @param nombre Nuevo nombre del inmueble.
     * @param descripcionDelInmueble Nueva descripción del inmueble.
     * @param precioPorNoche Nuevo precio por noche del inmueble.
     * @param otrosDetallesDelInmueble Nuevos detalles del inmueble.
     * @param direccion Nueva dirección del inmueble.
     * @param provincia Nueva provincia del inmueble.
     * @param reserva Nueva lista de reservas asociadas al inmueble.
     * @param serviciosExtras Nueva lista de servicios extras asociados al inmueble.
     * @param activa Nuevo estado de activación del inmueble.
     * @param imagenes Nueva lista de archivos de imágenes asociadas al inmueble.
     * @throws Exception Si ocurre un error durante la operación.
     */
    @Transactional
    public void editarInmueble(String id, String nombre,String descripcionDelInmueble, Long precioPorNoche,
                               String otrosDetallesDelInmueble, String direccion, Provincia provincia, List<Reserva> reserva,
                               List<InmuebleServicioExtra> serviciosExtras, boolean activa, List<MultipartFile> imagenes) throws Exception {
        validar(nombre,descripcionDelInmueble, precioPorNoche, otrosDetallesDelInmueble, direccion, provincia);
        Optional<Inmueble> inmuebleOptional = inmuebleRepositorio.findById(id);

        if (inmuebleOptional.isPresent()) {
            Inmueble inmueble = inmuebleOptional.get();
            inmueble.setNombre(nombre);
            inmueble.setDescripcionDelInmueble(descripcionDelInmueble);
            inmueble.setPrecioPorNoche(precioPorNoche);
            inmueble.setOtrosDetallesDelInmueble(otrosDetallesDelInmueble);
            inmueble.setDireccion(direccion);
            inmueble.setProvincia(provincia);
            inmueble.setActiva(activa);
            inmueble.setReservas(reserva);

            // Lógica para manejar las imágenes, por ejemplo, guardarlas en la base de datos o en el sistema de archivos.
            // Aquí asumimos que tienes un servicio de imágenes (imagenServicio) para manejar la lógica de guardar las imágenes.
            List<Imagen> imagenesGuardadas = new ArrayList<>();
            for (MultipartFile imagen : imagenes) {
                //consulto si la imagen que edite no esta en la lista que ya tiene el inmueble la guardo en la bd 
                if (!inmueble.getImagen().contains(imagen)) {
                    Imagen imagenGuardada = imagenServicio.guardar(imagen);
                    imagenGuardada.setInmueble(inmueble);//le asigno a quien pertenese esa imagen
                    imagenesGuardadas.add(imagenGuardada);
                }
            }
            // Asignar las imágenes al inmueble
            inmueble.setImagen(imagenesGuardadas);
            inmueble.setInmuebleServiciosExtras(serviciosExtras);
            inmuebleRepositorio.save(inmueble);
        }
    }

    public List<Inmueble> listaDeInmuebles() {
        try {
            return inmuebleRepositorio.findAll();
        }catch (Exception e) {
            return null;
        }
    }

    public Inmueble buscarInmueblePorId(String id) throws Exception {
        return inmuebleRepositorio.getReferenceById(id);
    }

    public List<Inmueble> buscarInmueblePorPrecio(Long precioPorNoche) {
        try {
            return inmuebleRepositorio.buscarInmueblePorPrecio(precioPorNoche);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Inmueble> buscarInmueblePorProvincia(Provincia provincia) {
        try {
            return inmuebleRepositorio.buscarInmueblePorProvincia(provincia);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Inmueble> buscarInmueblePorDireccion(String direccion) {
        try {
            return inmuebleRepositorio.buscarInmueblePorDireccion(direccion);
        } catch (Exception e) {
            return null;
        }
    }
  
    @Transactional
    public void borrarInmueble(String id){
        Optional<Inmueble> inmuebleOptional = inmuebleRepositorio.findById(id);
        try {
            if (inmuebleOptional.isPresent()) {
                inmuebleRepositorio.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Realiza la validación de los campos del inmueble.
     *
     * @param nombre Nombre del inmueble.
     * @param descripcionDelInmueble Descripción del inmueble.
     * @param precioPorNoche Precio por noche del inmueble.
     * @param otrosDetallesDelInmueble Otros detalles del inmueble.
     * @param direccion Dirección del inmueble.
     * @param provincia Provincia del inmueble.
     * @throws Exception Si algún campo no cumple con los requisitos.
     */
    private void validar(String nombre, String descripcionDelInmueble, Long precioPorNoche,
            String otrosDetallesDelInmueble, String direccion, Provincia provincia) throws Exception {

        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("EL nombre del inmueble no puede estar vacio");
        }
        if (descripcionDelInmueble == null || descripcionDelInmueble.isEmpty()) {
            throw new IllegalArgumentException("La descripción del inmueble no puede estar vacia");
        }
        if (precioPorNoche == null) {
            throw new IllegalArgumentException("El precio del inmueble no puede estar vacio");
        }
        if (otrosDetallesDelInmueble == null || otrosDetallesDelInmueble.isEmpty()) {
            throw new IllegalArgumentException("Los detalles extra del inmueble no pueden estar vacíos");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La direccion del inmueble no puede estar vacía");
        }
        if (provincia == null) {
            throw new IllegalArgumentException("La provincia del inmueble no puede estar vacía");
        }
    }
} // todo --> buscar inmueble por localidad
