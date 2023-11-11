package com.grupoK.Tukincho.entidades;

import com.Tukincho.Tukincho.enums.Provincia;
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
    @OneToMany
    private Reserva reserva;
}
