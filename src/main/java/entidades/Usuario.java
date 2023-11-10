package entidades;
import enums.Rol;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Data
public abstract class Usuario { // PREGUNTAR A JONA COMO HACER EL JOIN
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String id;
    private String nombre;
    private String email;
    private String password;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Rol rol;
}