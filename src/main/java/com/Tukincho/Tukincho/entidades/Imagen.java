package com.Tukincho.Tukincho.entidades;

import java.util.Base64;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    private String mime;
    private String nombre;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;
    
    public String generateBase64Image() {
        return Base64.getEncoder().encodeToString(this.contenido);
    }
}

