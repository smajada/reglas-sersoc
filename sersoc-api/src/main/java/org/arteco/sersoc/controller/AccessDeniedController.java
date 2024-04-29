package org.arteco.sersoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        // Redirige al usuario a una página de acceso denegado o a un recurso apropiado
        return "error/access-denied"; // Esta es la ruta al template de acceso denegado
    }
}
