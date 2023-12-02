package com.Tukincho.Tukincho.entidades;

import lombok.Data;
import javax.persistence.*;
import java.util.List;
/**
 * Clase que representa un propietario en el sistema.
 * Hereda de la clase Usuario y contiene información adicional sobre los inmuebles y reservas asociadas.
 *
 * @Entity Indica que esta clase es una entidad que se puede almacenar en una base de datos.
 * @Data Anotación de Lombok que genera automáticamente métodos toString, equals, hashCode, y otros métodos útiles.
 * @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) Especifica la estrategia de herencia, utilizando una tabla por clase.
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Propietario extends Usuario {
    @OneToMany(mappedBy = "propietario", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Inmueble> inmuebles;
    @OneToMany(mappedBy = "propietario", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Reserva> reserva;
    }


