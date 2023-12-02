package com.Tukincho.Tukincho.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * Clase que representa la relación entre un inmueble y un servicio extra en el sistema.
 * Contiene información sobre la asociación de un inmueble con un servicio extra y el precio asociado.
 *
 * @Entity Indica que esta clase es una entidad que se puede almacenar en una base de datos.
 * @Data Anotación de Lombok que genera automáticamente métodos toString, equals, hashCode, y otros métodos útiles.
 */

@Entity
@Data
public class InmuebleServicioExtra {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
    @ManyToOne
    @JoinColumn(name = "servicio_extra_id")
    private ServiciosExtra servicioExtra;
    private Long precio;
}
