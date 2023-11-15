package com.Tukincho.Tukincho.entidades;


import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Alexi
 */
@Entity
@Data

public class Reserva extends Usuario {
    @Id
    @GeneratedValue
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
    
   @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
   //un propietario puede tener muchas reservas corregir
    @OneToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaInicioReserva;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaFinReserva;
    private Double costoReserva;
    private Double costoServiciosSeleccionados;
    private Boolean activo;
}
