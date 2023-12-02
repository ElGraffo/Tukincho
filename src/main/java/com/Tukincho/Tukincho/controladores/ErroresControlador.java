package com.Tukincho.Tukincho.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * Controlador que maneja las páginas de error en la aplicación.
 */
@Controller
public class ErroresControlador implements ErrorController {
    
    /**
     * Método que renderiza la página de error según el código de error HTTP.
     *
     * @param httpRequest Objeto HttpServletRequest que contiene la información de la solicitud.
     * @return Modelo y vista de la página de error correspondiente.
     */
    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");

        String errorMsg = "";

        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "El recurso solicitado no existe";
                break;
            }

            case 403: {
                errorMsg = "No tiene permisos para acceder al recurso";
                break;
            }

            case 401: {
                errorMsg = "No se encuentra autorizado";
                break;
            }
            
            case 404:{
                        errorMsg = "El recurso solicitado no fue encontrado";
                        break;
                    }  

            case 500: {
                errorMsg = "Ocurrio un error interno";
            }
            
            case 505: {
                errorMsg = "Version no soportada";
            }
        }

        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;

    }
    
    /**
     * Método que obtiene el código de error HTTP de la solicitud.
     *
     * @param httpRequest Objeto HttpServletRequest que contiene la información de la solicitud.
     * @return Código de error HTTP.
     */
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code"); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Método que devuelve la ruta de la página de error.
     *
     * @return Ruta de la página de error.
     */

    public String getErrorPath() {
        return "/error.html";
    }

}
