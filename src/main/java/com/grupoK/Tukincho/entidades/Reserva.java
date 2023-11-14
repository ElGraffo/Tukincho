<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Reserva.java

package com.grupoK.Tukincho.entidades;
=======
package entidades;
>>>>>>> 6932bdf116a9242629f4ed4b2c5b1b2b3fc811cb:src/main/java/entidades/Reserva.java

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

/**
 *
 * @author Alexi
 */
@Entity
@Data
<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Reserva.java
public class Reserva extends Usuario {
=======
public class Reserva { // chequear los many to one o etc
>>>>>>> 6932bdf116a9242629f4ed4b2c5b1b2b3fc811cb:src/main/java/entidades/Reserva.java
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
