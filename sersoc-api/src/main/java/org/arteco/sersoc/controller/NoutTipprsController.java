package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.repository.NoutTipprsRepository;
import org.arteco.sersoc.service.NoutTipprsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tipo-prestacion")
public class NoutTipprsController extends AbstractCrudController<NoutTipprs, String, NoutTipprsRepository, NoutTipprsService> {

    protected NoutTipprsController(NoutTipprsService service) {
        super(service);
    }
}
