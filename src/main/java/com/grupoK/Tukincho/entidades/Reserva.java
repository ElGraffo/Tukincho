package com.grupoK.Tukincho.entidades;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

/**
 *
 * @author Alexi
 */
@Entity
@Data
public class Reserva {
    @Id
    @GeneratedValue
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
    
   @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

   @OneToOne
   @JoinColumn(name = "propietario_id")
    private Propietario propietario;
    
    private Date fechaInicioReserva;
    private Date fechaFinReserva;
    private Double costoReserva;
    private Double costoServiciosSeleccionados;
    private Boolean activo;
}
