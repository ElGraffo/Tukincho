
package entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    
    private Date fechaInicioReserva;
    private Date fechaFinReserva;
    private Double costoReserva;
    private Double costoServiciosSeleccionados;
    private Boolean activo;
}
