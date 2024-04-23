package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.repository.NoutReglesRepository;
import org.arteco.sersoc.service.NoutReglesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/regla")
public class NoutReglesController extends AbstractCrudController<NoutRegles, Long, NoutReglesRepository, NoutReglesService> {

        private final NoutReglesService noutReglesService;

        public NoutReglesController(NoutReglesService service) {
            super(service);
            this.noutReglesService = service;
        }

        @GetMapping("/prestacion/{idTipPrestacion}")
        public List<NoutRegles> findByIdPrestacion(@PathVariable String idTipPrestacion){
            return noutReglesService.findByIdPrestacion(idTipPrestacion);
        }

}
