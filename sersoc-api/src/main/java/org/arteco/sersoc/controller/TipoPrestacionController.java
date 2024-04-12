package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.entities.TipoPrestacionEntity;
import org.arteco.sersoc.repository.TipoPrestacionRepository;
import org.arteco.sersoc.service.TipoPrestacionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tipo-prestacion")
public class TipoPrestacionController extends AbstractCrudController<TipoPrestacionEntity, Long, TipoPrestacionRepository, TipoPrestacionService> {

    protected TipoPrestacionController(TipoPrestacionService service) {
        super(service);
    }
}
