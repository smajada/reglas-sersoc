package org.arteco.sersoc.controller;

import org.arteco.sersoc.utils.AuthenticateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/public")
    public String publicPage(Model model) {

        AuthenticateUtils.addAuthenticatedAttribute(model);
        model.addAttribute("titlePage", "Public Page");

        return "autenticate/prueba";  // Updated path to the public template
    }

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("titlePage", "Login");
        model.addAttribute("username", AuthenticateUtils.getUsername());

        if (AuthenticateUtils.isAuthenticated()) {
            return "redirect:/home";
        }
        return "autenticate/login";  // Updated path to the login template
    }


    // Handler for the home page
    @GetMapping("/home")
    public String home(Model model) {
        AuthenticateUtils.addAuthenticatedAttribute(model); // Add the authenticated attribute to the model
        model.addAttribute("titlePage", "Home");
        model.addAttribute("username", AuthenticateUtils.getUsername());
        return "autenticate/home";  // Updated path to the home template
    }

}
