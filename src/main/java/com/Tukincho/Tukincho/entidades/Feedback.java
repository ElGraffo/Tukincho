
package com.Tukincho.Tukincho.entidades;


import java.util.List;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Data
@Entity
public class Feedback {
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String id;
    
    private String titulo;
    private String detalle;
    private Integer calificacion;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Imagen> imagen;
    
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private Boolean activo;
    
    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", detalle='" + detalle + '\'' +
                ", calificacion=" + calificacion +
                // Evitar imprimir la relación inversa de Usuario
                // Evitar imprimir la relación inversa de Inmueble
                // Evitar imprimir la relación inversa de Imagen
                '}';
    }
}
