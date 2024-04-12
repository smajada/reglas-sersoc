package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.entities.PrestacionEntity;
import org.arteco.sersoc.repository.PrestacionRepository;
import org.arteco.sersoc.service.PrestacionService;
import org.springframework.stereotype.Controller;

@Controller
public class PrestacionController extends AbstractCrudController<PrestacionEntity, Long, PrestacionRepository, PrestacionService> {

    protected PrestacionController(PrestacionService service) {
        super(service);
    }
}
