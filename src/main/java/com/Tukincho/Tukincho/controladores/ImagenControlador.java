package com.Tukincho.Tukincho.controladores;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**Este controlador manejara las solicitudes relacionadas 
    *a la obtención de imágenes de perfil de usuario. Accede a "/imagen/perfil/{id}", 
    *y busca a un usuario por su identificador (@param id), obtiene el contenido de la imagen 
    * y la devuelve en formato de bytes con las cabeceras adecuadas.
    * @param id busca id del usuario
    * @return ResponseEntity<> devuelve el contenido de la imagen con los headers y su estado http
    */

@Controller
@RequestMapping("/imagen")

public class ImagenControlador {
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable String id){
        
     Usuario usuario = usuarioServicio.buscarUsuarioPorId(id);
              
     byte[] imagen = usuario.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    
    
}
}
