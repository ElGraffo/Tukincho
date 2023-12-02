package com.Tukincho.Tukincho.servicios;


import com.Tukincho.Tukincho.entidades.*;
import com.Tukincho.Tukincho.repositorios.FeedbackRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio que gestiona las operaciones relacionadas con los feedbacks en el sistema.
 *
 * @Service Indica que esta clase es un servicio de Spring que puede ser inyectado en otras clases.
 */
@Service
public class FeedbackServicio {
    @Autowired
    FeedbackRepositorio feedbackRepositorio;
     /**
     * Crea un nuevo feedback con la información proporcionada.
     *
     * @param titulo Título del feedback.
     * @param detalle Detalle o contenido del feedback.
     * @param imagenes Lista de imágenes asociadas al feedback.
     * @param calificacion Calificación asignada al feedback.
     * @param usuario Usuario que proporciona el feedback.
     * @param inmueble Inmueble al que está asociado el feedback.
     */
    @Transactional
    public void crearFeedback (String titulo, String detalle, List<Imagen> imagenes, Integer calificacion, Usuario usuario, Inmueble inmueble){
        Feedback feedback = new Feedback();
        feedback.setTitulo(titulo);
        feedback.setDetalle(detalle);
        feedback.setUsuario(usuario);
        feedback.setInmueble(inmueble);
        //TODO si existe un comentario del usuario para el id del inmueble pasado por paramentro, no puede volver a comentar
        feedback.setActivo(true);
        if(imagenes!=null && !imagenes.isEmpty()){
            feedback.setImagen(imagenes);
        }
        feedback.setCalificacion(calificacion);
        try{
            feedbackRepositorio.save(feedback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
     /**
     * Edita un feedback existente con la información proporcionada.
     *
     * @param id Identificador único del feedback a editar.
     * @param titulo Nuevo título del feedback.
     * @param detalle Nuevo detalle o contenido del feedback.
     * @param imagenes Nueva lista de imágenes asociadas al feedback.
     * @param calificacion Nueva calificación asignada al feedback.
     */
    public void editarFeedback(String id, String titulo, String detalle, List<Imagen> imagenes, Integer calificacion){
        Optional<Feedback> feedbackOptional = feedbackRepositorio.findById(id);
        if(feedbackOptional.isPresent()){
            Feedback feedback = feedbackOptional.get();
            feedback.setTitulo(titulo);
            feedback.setDetalle(detalle);
            feedback.setCalificacion(calificacion);
            if(imagenes!=null||!imagenes.isEmpty()){
                feedback.setImagen(imagenes);
            }
            try{
                feedbackRepositorio.save(feedback);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
     /**
     * Busca un feedback por su identificador único.
     *
     * @param id Identificador único del feedback a buscar.
     * @return Feedback encontrado, o null si no se encuentra.
     */
    public Feedback buscarFeedback(String id){

        Optional<Feedback> feedbackOptional = feedbackRepositorio.findById(id);
        if(feedbackOptional.isPresent()){
            return feedbackOptional.get();
        }
        return null;
    }
     /**
     * Desactiva un feedback existente por su identificador único.
     *
     * @param id Identificador único del feedback a desactivar.
     */
    public void desactivarFeedback(String id){
        Optional <Feedback> feedbackOptional = feedbackRepositorio.findById(id);
        if(feedbackOptional.isPresent()){
            Feedback feedback = feedbackOptional.get();
            feedback.setActivo(false);
            try{
                feedbackRepositorio.save(feedback);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
