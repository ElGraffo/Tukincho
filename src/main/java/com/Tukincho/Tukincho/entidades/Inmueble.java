package com.Tukincho.Tukincho.entidades;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;
import com.Tukincho.Tukincho.enums.Provincia;
import java.util.ArrayList;

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
    private String nombre;
    
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
    
   @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InmuebleServicioExtra> inmuebleServiciosExtras = new ArrayList<>();

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reserva> reserva;
    
    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Imagen> imagen;

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedback;
    

    // todo --> agregar lista de reseñas / agregar localidad

}
