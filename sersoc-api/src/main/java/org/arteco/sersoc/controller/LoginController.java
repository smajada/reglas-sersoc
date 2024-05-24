package org.arteco.sersoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("titlePage", "Login");
        return "autenticate/login";
    }

    @RequestMapping({"/home", "/"})
    public String index(Model model) {

        model.addAttribute("titlePage", "Home");
        return "autenticate/home";
    }


    @RequestMapping("/access-denied")
    public String accessDenied(Model model) {

        model.addAttribute("titlePage", "Access Denied");
        return "error/access-denied";
    }

    @RequestMapping("/not-found")
    public String notFound(Model model) {

        model.addAttribute("titlePage", "Not-found");
        return "error/not-found";
    }

}
