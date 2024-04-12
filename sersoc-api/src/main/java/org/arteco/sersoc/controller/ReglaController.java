package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaEntity;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaRepository;
import org.arteco.sersoc.service.ReglaService;
import org.arteco.sersoc.service.TipoPrestacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/regla")
public class ReglaController extends AbstractCrudController<ReglaEntity, Long, ReglaRepository, ReglaService> {

        private final ReglaService reglaService;

        public ReglaController(ReglaService service) {
            super(service);
            this.reglaService = service;
        }

//    @GetMapping("/")
//    public String listAllReglas(Model model){
//            Iterable<ReglaEntity> reglasList = service.findAll();
//
//            model.addAttribute("reglas", reglasList);
//            model.addAttribute("titlePage", "Reglas");
//
//            return  "reglas/reglas";
//    }

    @GetMapping("/crear-regla")
    public String crearReglas(Model model){
        ReglaEntity regla = new ReglaEntity();
        model.addAttribute("regla", regla);
        model.addAttribute("titlePage", "Nueva regla");
        return "reglas/crear_regla";
    }

    @GetMapping("/editar/{id}")
    public String editarReglas(@PathVariable Long id, Model model){
        model.addAttribute("regla", service.findById(id).get());
        model.addAttribute("titlePage", "Editar regla");
        return "reglas/editar_regla";
    }

    @PostMapping("/reglas")
    public String guardarReglas(@ModelAttribute("regla") ReglaEntity regla){
        if (regla.getId() == null){
            service.save(regla);
        } else {
            service.update(regla, regla.getId());
        }

        return "redirect:/regla/";
    }
}
