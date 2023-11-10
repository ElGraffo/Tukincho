package servicios;

import entidades.Reserva;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.ReservaRepositorio;

/**
 * 
 * @author Jonatan Atencio
 * @version 1.0
 * @date 9/11/2023
 */
@Service
public class ReservaServicio {
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    
    
    
    
    @Transactional
    public Reserva crearReserva(Inmueble inmueble,Usuario usuario,Date fechaInicioReserva,
        Date fechaFinReserva,Double costoReserva, Double costoServiciosSeleccionados)throws Exception{
        
        validarReserva(inmueble, usuario, fechaInicioReserva, fechaInicioReserva, 
                costoReserva, costoServiciosSeleccionados);
        
        Reserva reserva = new Reserva();
        
        reserva.setInmueble(inmueble);
        reserva.setUsuario(usuario);
        reserva.setFechaInicioReserva(fechaInicioReserva);
        reserva.setFechaFinReserva(fechaFinReserva);
        reserva.setCostoReserva(costoReserva);
        reserva.setCostoServiciosSeleccionados(costoServiciosSeleccionados);
        
        return reservaRepositorio.save(reserva);
      
    }
    
    
    
    
    @Transactional
    public void eliminarReserva(String id) throws Exception{
        Optional<Reserva> respuesta = reservaRepositorio.findById(id);
        if(respuesta.isPresent()){
            reservaRepositorio.deleteById(id);
        }else{
            throw new Exception("No se encontrol la reserva a eliminar con el id: "+id);
        }
    }
    
    
    
    
    @Transactionl
    public void modificarReserva(String id,Inmueble inmueble, Usuario usuario, Date fechaInicioReserva, Date fechaFinReserva,
                Double costoReserva, Double costoServiciosSeleccionados, Boolean activo) throws Exception{
        
        Optional<Reserva> respuesta = reservaRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Reserva reserva = new Reserva();
            
            reserva.setInmueble(inmueble);
            reserva.setUsuario(usuario);
            reserva.setFechaInicioReserva(fechaInicioReserva);
            reserva.setFechaFinReserva(fechaFinReserva);
            reserva.setCostoReserva(costoReserva);
            reserva.setCostoServiciosSeleccionados(costoServiciosSeleccionados);
            reserva.setActivo(activo);
            
            reservaRepositorio.save(reserva);
        }
    }
    
    /**
     * Devuelve una reserva que sea igual al id pasado como argumento
     * @param id = el di que corresponde a la reserva que se quiere encontrar
     * @return devuelve un objeto Reserva
     * @throws Exception lanza una exepcion si no se encontro la reserva con el id buscado
     */
    public Reserva buscarReservaById(String id) throws Exception{
        return reservaRepositorio.getReferenceById(id);
    }
    
    /**
     * Este metodo devuelve una lista de reserva que corresponda al id del usuario que tenga 
     * una reserva., deberia agregar un parametro activo para filtrar por reservas activas
     * o inactivas, activa seria si aun no finalizo con el tiempo de reserva programado.
     * @param idUsuario para buscar por el id del que corresponde a un usuario
     * @return retorna una lista con reservas correspondiente a un usuario
     * @throws Exception arroja excepcion si no se encontro una reserva que corresponda 
     * a una reserva que contenga un usuario con ese id.
     */
    public List<Reserva> buscarPorIdUsuario(String idUsuario) throws Exception{
        return reservaRepositorio.findByUsuarioId(idUsuario);
    }
    
    
     /**
     * Este método devuelve una lista de reservas correspondientes a la fecha de inicio.
     *
     * @param fechaInicio para buscar reservas con esta fecha de inicio es tipo Date
     * @return una lista con reservas que tienen la fecha de inicio especificada tipo List<Reserva>
     */
    public List<Reserva> buscarPorFechaInicioReserva(Date fechaInicio) {
        return reservaRepositorio.findByFechaInicioReserva(fechaInicio);
    }

    /**
     * Este método devuelve una lista de reservas correspondientes a la fecha de fin.
     *
     * @param fechaFin para buscar reservas con esta fecha de fin, tipo Date
     * @return una lista con reservas que tienen la fecha de fin especificada, tipo List<Reserva>
     */
    public List<Reserva> buscarPorFechaFinReserva(Date fechaFin) {
        return reservaRepositorio.findByFechaFinReserva(fechaFin);
    }
    
    
    
    /**
     * Este método devuelve una lista de reservas correspondientes al ID del inmueble.
     *
     * @param inmuebleId para buscar por el ID del inmueble, tipo String.
     * @return una lista con reservas correspondientes al inmueble, tipo List<Reserva>.
     */
    public List<Reserva> buscarPorIdInmueble(String inmuebleId) {
        return reservaRepositorio.findByInmuebleId(inmuebleId);
    }

    
    
    /**
     * Este metodo valida que no halla campos vacios o nulos y que la fecha de reserva no este ocupada
     * por otra reserva.
     * @param inmueble
     * @param usuario
     * @param fechaInicioReserva
     * @param fechaFinReserva
     * @param costoReserva
     * @param costoServiciosSeleccionados
     * @throws Exception 
     */
    public void validarReserva(Inmueble inmueble, Usuario usuario, Date fechaInicioReserva,
            Date fechaFinReserva, Double costoReserva, Double costoServiciosSeleccionados) throws Exception {
    if (inmueble == null) {
        throw new Exception("El inmueble no puede ser nulo");
    }

    if (usuario == null) {
        throw new Exception("El usuario no puede ser nulo");
    }

    if (fechaInicioReserva == null || fechaInicioReserva.before(new Date())) {
        throw new Exception("La fecha de inicio de reserva no puede ser nula o una fecha pasada");
    }

    if (fechaFinReserva == null || fechaFinReserva.before(new Date()) || fechaFinReserva.before(fechaInicioReserva)) {
        throw new Exception("La fecha de fin de reserva no puede ser nula, una fecha pasada o anterior a la fecha de inicio de la reserva");
    }

    if (costoReserva == null || costoReserva <= 0) {
        throw new Exception("El costo de la reserva no puede ser nulo, negativo o cero");
    }

    if (costoServiciosSeleccionados == null || costoServiciosSeleccionados < 0) {
        throw new Exception("El costo de los servicios seleccionados no puede ser nulo o negativo");
    }

    // Verificar si hay reservas existentes para el inmueble en la fecha especificada
    if (reservaRepositorio.existeReservaEnFecha(inmueble.getId(), fechaInicioReserva)) {
        throw new Exception("Ya existe una reserva para este inmueble en la fecha especificada");
    }
}

    
    /**
     * este metodo devuelve verdadero si ya existe una reserva para la fecha seleccionada
     * en ese caso no podria reservar en esa fecha, en otro caso devuelve false.
     * @param idInmueble el id del inmueble por el cual se desea verificar si esta disponible para tal fecha.
     * @param fechaInicioReserva la fecha por la cual se consultara si esta disponible un inmueble para reservar.
     * @return retorna verdadero si ya esta reservado para la fecha dada o falso si no hay reserva para esa fecha.
     */
    public Boolean existeReservaEnFecha(String idInmueble, Date fechaInicioReserva){
        return reservaRepositorio.existeReservaEnFecha(idInmueble, fechaInicioReserva);
    }
    
    
    /**
     * Cambia el estado de una reserva a activo o inactivo.
     *  Si ya esta activo pasa a inactivo y viceversa.
     * @param reservaId  ID de la reserva que se va a actualizar.
     * @param activo     Nuevo estado de la reserva.
     * @throws Exception Si no se encuentra la reserva con el ID proporcionado.
     */
    public void cambiarEstadoReserva(String reservaId, boolean activo) throws Exception {
        reservaRepositorio.cambiarEstadoReserva(reservaId, activo);
    }
    
    
    /**
     * Obtiene todas las reservas, ya sean activas o no.
     *
     * @return Lista de todas las reservas.
     */
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepositorio.findAllReservas();
    }

    /**
     * Obtiene todas las reservas activas.
     *
     * @return Lista de reservas activas.
     */
    public List<Reserva> obtenerReservasActivas() {
        return reservaRepositorio.findAllReservasActivas();
    }
}
