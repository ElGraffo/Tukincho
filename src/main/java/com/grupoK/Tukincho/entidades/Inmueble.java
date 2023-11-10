package com.grupoK.Tukincho.entidades;

import com.grupoK.Tukincho.enums.Provincia;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Inmueble {
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String id;
    @ManyToOne
    @JoinColumn(name = "propietario_id")
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
    private List<Reserva> reserva;
    @OneToMany
    private List <Imagen> imagen;
    // todo --> agregar lista de reseñas / agregar localidad
}
