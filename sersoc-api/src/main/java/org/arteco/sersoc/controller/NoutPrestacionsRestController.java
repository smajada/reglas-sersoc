package org.arteco.sersoc.controller;

import java.util.Map;
import org.arteco.sersoc.base.AbstractSimpleCrudController;
import org.arteco.sersoc.dto.NoutPrestacionsDTO;
import org.arteco.sersoc.filter.BasicFilter;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.repository.NoutPrestacionsRepository;
import org.arteco.sersoc.service.NoutPrestacionsService;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NoutPrestacionsRestController extends
	AbstractSimpleCrudController<NoutPrestacions, Long, BasicFilter, NoutPrestacionsRepository, NoutPrestacionsService> {

	private final ReglaTipoPrestacionService reglaTipoPrestacionService;

	public NoutPrestacionsRestController(NoutPrestacionsService service, ReglaTipoPrestacionService reglaTipoPrestacionService) {

		super(service);
		this.reglaTipoPrestacionService = reglaTipoPrestacionService;
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<NoutPrestacionsDTO> getPrestacionById(@PathVariable Long id) {

		Optional<NoutPrestacionsDTO> optionalPrestacion = service.findByIdPerPrs(id);

		if (optionalPrestacion.isPresent()) {
			NoutPrestacionsDTO prestacion = optionalPrestacion.get();

			for (Map<String, Object> beneficiario : prestacion.getBeneficiaris()) {
				Map<String, Object> padron = reglaTipoPrestacionService.findPadron((String) beneficiario.get("dni"));
				beneficiario.put("padron", padron);
			}

			return ResponseEntity.ok(prestacion);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}