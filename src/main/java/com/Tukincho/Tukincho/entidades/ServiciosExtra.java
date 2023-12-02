package com.Tukincho.Tukincho.entidades;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
/**
 * Clase que representa servicios extra disponibles en el sistema.
 *
 * @Entity Indica que esta clase es una entidad que se puede almacenar en una base de datos.
 * @Data Anotación de Lombok que genera automáticamente métodos toString, equals, hashCode, y otros métodos útiles.
 */
@Entity
@Data
public class ServiciosExtra {
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String id;
    private String nombreDelServicioExtra;
}

    

