package com.Tukincho.Tukincho.servicios;


import com.Tukincho.Tukincho.entidades.*;
import com.Tukincho.Tukincho.repositorios.FeedbackRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FeedbackServicio {
    @Autowired
    FeedbackRepositorio feedbackRepositorio;

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

    public Feedback buscarFeedback(String id){

        Optional<Feedback> feedbackOptional = feedbackRepositorio.findById(id);
        if(feedbackOptional.isPresent()){
            return feedbackOptional.get();
        }
        return null;
    }

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