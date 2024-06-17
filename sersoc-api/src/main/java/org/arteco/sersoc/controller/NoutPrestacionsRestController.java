package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractSimpleCrudController;
import org.arteco.sersoc.dto.NoutPrestacionsDTO;
import org.arteco.sersoc.filter.BasicFilter;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.repository.NoutPrestacionsRepository;
import org.arteco.sersoc.service.NoutPrestacionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NoutPrestacionsRestController extends AbstractSimpleCrudController<NoutPrestacions, Long, BasicFilter, NoutPrestacionsRepository, NoutPrestacionsService> {

    public NoutPrestacionsRestController(NoutPrestacionsService service) {
        super(service);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<NoutPrestacionsDTO> getPrestacionById(@PathVariable Long id) {
        Optional<NoutPrestacionsDTO> optionalPrestacion = service.findByIdPerPrs(id);

        return optionalPrestacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}