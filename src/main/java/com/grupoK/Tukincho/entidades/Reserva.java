<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Reserva.java

package com.GrupoK.Tukincho.entidades;
=======
package com.Tukincho.Tukincho.entidades;
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/entidades/Reserva.java

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

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

<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Reserva.java
=======
   //un propietario puede tener muchas reservas corregir
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/entidades/Reserva.java
   @OneToOne
   @JoinColumn(name = "propietario_id")
    private Propietario propietario;
    
    private Date fechaInicioReserva;
    private Date fechaFinReserva;
    private Double costoReserva;
    private Double costoServiciosSeleccionados;
    private Boolean activo;
}
