package com.Tukincho.Tukincho.entidades;

import com.Tukincho.Tukincho.enums.Provincia;
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
    @JoinColumn(name = "id_propietario")
    private Propietario propietario;
    
    @Column(length = 500)
    private String descripcionDelInmueble;
    @Basic
    private Long precioPorNoche;
    @Column(length = 500)
    private String otrosDetallesDelInmueble;
    @Basic
    private String direccion;
    @Enumerated(EnumType.STRING)
    private Provincia provincia;
    @Basic
    private boolean activa;
    
    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reserva> reserva;
    
    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Imagen> imagen;
    // todo --> agregar lista de reseñas / agregar localidad
}
