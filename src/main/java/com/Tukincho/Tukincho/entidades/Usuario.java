
package com.Tukincho.Tukincho.entidades;

import com.Tukincho.Tukincho.enums.Rol;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)

public class Usuario {

    /**Esta entidad  
    *a la obtención de imágenes de perfil de usuario. Accede a "/imagen/perfil/{id}", 
    *y busca a un usuario por su identificador (@param id), obtiene el contenido de la imagen 
    * y la devuelve en formato de bytes con las cabeceras adecuadas.
    * @param id busca id del usuario
    * 
    */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombreUsuario;
    private String email;
    private String password;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;

    @OneToMany
    @JoinColumn(name = "reserva_id")
    private List<Reserva> reserva;

    @OneToMany
    @JoinColumn(name = "feedback_id")
    private List<Feedback> feedback;
}
