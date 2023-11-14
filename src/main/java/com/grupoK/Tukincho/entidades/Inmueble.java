<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Inmueble.java
package com.grupoK.Tukincho.entidades;
=======
package entidades;
>>>>>>> 6932bdf116a9242629f4ed4b2c5b1b2b3fc811cb:src/main/java/entidades/Inmueble.java

import enums.Provincia;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
public class Inmueble {
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String id;
    @ManyToOne
    private Propietario propietario;
    @Basic
    private String descripcionDelInmueble;
    @Basic
    private Long precioPorNoche;
    @Basic
    private String otrosDetallesDelInmueble;
    @Basic
    private String direccion;
    @Enumerated(EnumType.STRING)
    private Provincia provincia;
    @Basic
    private boolean activa;
    @ManyToOne // charlar hoy
    private Reserva reserva;
}
