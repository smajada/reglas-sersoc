package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.repository.NoutPrestacionsRepository;
import org.arteco.sersoc.service.NoutPrestacionsService;
import org.springframework.stereotype.Controller;

@Controller
public class NoutPrestacionsController extends AbstractCrudController<NoutPrestacions, Long, NoutPrestacionsRepository, NoutPrestacionsService> {

    protected NoutPrestacionsController(NoutPrestacionsService service) {
        super(service);
    }
}
