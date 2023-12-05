package com.Tukincho.Tukincho.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

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