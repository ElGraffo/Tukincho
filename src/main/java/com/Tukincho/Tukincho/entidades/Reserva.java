package com.Tukincho.Tukincho.entidades;


import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Alexi
 */

/**
 * Clase que representa una reserva en el sistema.
 *
 * @Entity Indica que esta clase es una entidad que se puede almacenar en una base de datos.
 * @Data Anotación de Lombok que genera automáticamente métodos toString, equals, hashCode, y otros métodos útiles.
 */

@Entity
@Data

public class Reserva{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioReserva;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinReserva;
    private Long costoReserva;
    private Double costoServiciosSeleccionados;
    private Boolean activo;
}
