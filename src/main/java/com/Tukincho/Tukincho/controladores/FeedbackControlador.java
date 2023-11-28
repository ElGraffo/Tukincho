package com.Tukincho.Tukincho.controladores;


import com.Tukincho.Tukincho.entidades.*;
import com.Tukincho.Tukincho.servicios.FeedbackServicio;
import com.Tukincho.Tukincho.servicios.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/feedback")



public class FeedbackControlador {

    @Autowired
    ReservaServicio reservaServicio;


    @Autowired
    FeedbackServicio feedbackServicio;
    @GetMapping("/crear/{id}")
    public String feedback(@PathVariable String id, ModelMap model) {
        try {
            Reserva reserva = reservaServicio.buscarReservaById(id);

            model.put("reserva", reserva);
            return "feedback.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "feedback.html";
        }
    }





    @PostMapping("/reseniando")
    public String reseniando(
            @RequestParam String titulo,
            @RequestParam String detalle,
            @RequestParam Integer calificacion,
            @RequestParam String reservaId,


            ModelMap model){


        try {

            List<Imagen> imagenes= new ArrayList<Imagen>();

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            Reserva reserva=reservaServicio.buscarReservaById(reservaId);
            Usuario usuario=reserva.getUsuario();
            Inmueble inmueble= reserva.getInmueble();

            System.out.println("reservaid"+reserva.getId());
            System.out.println("reservausu"+usuario.getNombreUsuario());
            System.out.println("reservainmueble"+inmueble.getNombre());

         feedbackServicio.crearFeedback(titulo, detalle, imagenes, calificacion, usuario, inmueble);

            model.put("exito","El feedback se ha generado exitosamente");
        } catch (Exception e) {
            model.put("error","Ha habido un error, vuelva a intentarlo nuevamente");
            System.out.println("##############################ERROR feedbackA-----------------------------------------------------");
            System.out.println(e.getMessage());
        }
        return "index.html";
    }
}
