package org.arteco.sersoc.controller;

import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ReglaTipoPrestacionRepository reglaTipoPrestacionRepository;

    public TestController(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository) {
        this.reglaTipoPrestacionRepository = reglaTipoPrestacionRepository;
    }

    @GetMapping()
    public List<ReglaTipoPrestacionEntity> listAll(){
        return reglaTipoPrestacionRepository.findAll();
    }
}
