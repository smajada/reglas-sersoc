package org.arteco.sersoc.controller;

import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.NoutPrestacionsRepository;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ReglaTipoPrestacionRepository reglaTipoPrestacionRepository;
    private final NoutPrestacionsRepository noutPrestacionsRepository;

    public TestController(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository, NoutPrestacionsRepository noutPrestacionsRepository) {
        this.reglaTipoPrestacionRepository = reglaTipoPrestacionRepository;
        this.noutPrestacionsRepository = noutPrestacionsRepository;
    }

    @GetMapping()
    public List<ReglaTipoPrestacionEntity> listAll(){
        return reglaTipoPrestacionRepository.findAll();
    }

    @GetMapping("/prestacion/")
    public List<NoutPrestacions> listAllPrestacion(){
        return noutPrestacionsRepository.findAll();
    }
}
