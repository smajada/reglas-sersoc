package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.entities.ReglaEntity;
import org.arteco.sersoc.repository.ReglaRepository;
import org.arteco.sersoc.service.ReglaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/regla")
public class ReglaController extends AbstractCrudController<ReglaEntity, Long, ReglaRepository, ReglaService> {

        private final ReglaService reglaService;

        public ReglaController(ReglaService service) {
            super(service);
            this.reglaService = service;
        }

        @GetMapping("/prestacion/{idPrestacion}")
        public List<ReglaEntity> findByIdPrestacion(@PathVariable Long idPrestacion){
            return reglaService.findByIdPrestacion(idPrestacion);
        }

}
