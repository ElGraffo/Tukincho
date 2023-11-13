package com.Tukincho.Tukincho.controladores;

import com.Tukincho.Tukincho.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";

    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            String password2, ModelMap modelo) throws Exception {
        try {
            System.out.println(nombre);
            usuarioServicio.registrar(nombre, email, password, password2);
            System.out.println("registrando ok");
            modelo.put("exito", "Usuario registrado correctamente!!!!");
            return "index.html";
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            modelo.put("error", e.getMessage());
            return "registro.html";
        }
    }
}
