package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/regla-tipo-prestacion")
public class ReglaTipoPrestacionController extends AbstractCrudController<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository, ReglaTipoPrestacionService> {

    protected ReglaTipoPrestacionController(ReglaTipoPrestacionService service) {
        super(service);
    }

    @GetMapping("/list")
    public String listAllReglasTipoPrestacion(Model model){
        Iterable<ReglaTipoPrestacionEntity> reglasTipoPrestacionList = service.findAll();

//        for (ReglaTipoPrestacionEntity reglaTipoPrestacion : reglasTipoPrestacionList) {
//            System.out.println(reglaTipoPrestacion);
//        }
        model.addAttribute("reglasTipoPrestacion", reglasTipoPrestacionList);
        model.addAttribute("titlePage", "Reglas Tipo Prestacion");

        return  "reglas/reglas";
    }
}
