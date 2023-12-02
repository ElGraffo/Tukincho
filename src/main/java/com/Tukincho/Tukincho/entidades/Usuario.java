
package com.Tukincho.Tukincho.entidades;

import com.Tukincho.Tukincho.enums.Rol;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
    /**
     * Esta clase representara a un usuario en el sistema.
     * Contiene información como el nombre de usuario, correo electrónico, contraseña, estado de activo/inactivo,
     * rol, imagen de perfil, reservas y retroalimentaciones asociadas.
     *
     * @Entity Indica que esta clase es una entidad que se puede almacenar en una base de datos.
     * @Data Anotación de Lombok que genera automáticamente métodos toString, equals, hashCode, y otros métodos útiles.
     * @Inheritance(strategy = InheritanceType.JOINED) Especifica la estrategia de herencia, utilizando JOINED.
     */
@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)

public class Usuario {

    
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
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reserva;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedback;
    
    @Override
    public String toString(){
        return "usuario["+
                "id"+id+
                "NombreUsuario"+nombreUsuario+
                "email"+email+
                "activo?:"+activo+
                "Rol:"+rol+"]";
                
    }
}
