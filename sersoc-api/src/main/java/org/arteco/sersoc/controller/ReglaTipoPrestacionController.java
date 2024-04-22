package org.arteco.sersoc.controller;

import jakarta.persistence.EntityNotFoundException;
import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.dto.PageDto;
import org.arteco.sersoc.dto.ReglaDTO;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.service.NoutPrestacionsService;
import org.arteco.sersoc.service.NoutReglesService;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;
import org.arteco.sersoc.service.NoutTipprsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/regla-tipo-prestacion")
public class ReglaTipoPrestacionController extends AbstractCrudController<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository, ReglaTipoPrestacionService> {

    private final NoutTipprsService noutTipprsService;
    private final NoutReglesService noutReglesService;
    private final NoutReglesController noutReglesController;
    private final NoutPrestacionsService noutPrestacionsService;

    public ReglaTipoPrestacionController(ReglaTipoPrestacionService service,
                                         NoutTipprsService noutTipprsService,
                                         NoutReglesService noutReglesService, NoutReglesController noutReglesController, NoutPrestacionsService noutPrestacionsService) {
        super(service);
        this.noutTipprsService = noutTipprsService;
        this.noutReglesService = noutReglesService;
        this.noutReglesController = noutReglesController;
        this.noutPrestacionsService = noutPrestacionsService;
    }

    @GetMapping("/list")
    public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, 30);
        Iterable<ReglaTipoPrestacionEntity> reglasTipoPrestacionPage = super.service.findAll();
        PageDto<NoutRegles> reglesPage = noutReglesController.page(pageRequest);

        model.addAttribute("reglas", reglesPage);
        model.addAttribute("reglasTipoPrestacion", reglasTipoPrestacionPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("titlePage", "Reglas ");
        return "reglas/reglas";
    }

    @GetMapping("/editar/{reglaId}")
    public String edit(@PathVariable("reglaId") Long reglaId, Model model) {
        NoutRegles regla = this.noutReglesService.findById(reglaId).orElseThrow(EntityNotFoundException::new);

        List<NoutTipprs> allTipoPrestacion = (List<NoutTipprs>) noutTipprsService.findAll();

        List<ReglaTipoPrestacionEntity> allReglasTipoPrestacion = (List<ReglaTipoPrestacionEntity>) super.service.findAll();

        List<String> reglasTipoPrestacionList = allReglasTipoPrestacion
                .stream()
                .filter(reglaTipoPrestacion ->
                        reglaTipoPrestacion.getNoutRegles().getCon().equals(regla.getCon())
                        && reglaTipoPrestacion.getActive())
                .map(reglaTipoPrestacion ->
                        reglaTipoPrestacion.getNoutTipprs().getCoa())
                .collect(Collectors.toList());

        ReglaDTO reglaDTO = new ReglaDTO();
        reglaDTO.setRegla(regla);
        reglaDTO.setAllTipoPrestacion(allTipoPrestacion);
        reglaDTO.setReglasTipoPrestacionList(reglasTipoPrestacionList);

        model.addAttribute("reglaDTO", reglaDTO);
        model.addAttribute("titlePage", "Editar regla");
        return "reglas/editar_regla";
    }

    @GetMapping("/delete/{reglaId}")
    public String delete(@PathVariable("reglaId") Long reglaId) {
        NoutRegles regla = this.noutReglesService.findById(reglaId).orElseThrow(EntityNotFoundException::new);

        this.noutReglesService.delete(regla);
        return "redirect:/regla-tipo-prestacion/list";
    }

    @GetMapping("/crear")
    public String createView(Model model) {
        ReglaDTO reglaDTO = new ReglaDTO();
        reglaDTO.setRegla(new NoutRegles());
        reglaDTO.setAllTipoPrestacion(new ArrayList<>());
        reglaDTO.setReglasTipoPrestacionList(new ArrayList<>());

        Iterable<NoutTipprs> tipoPrestacionList = noutTipprsService.findAll();

        model.addAttribute("reglaDTO", reglaDTO);
        model.addAttribute("tipoPrestacionList", tipoPrestacionList);
        model.addAttribute("titlePage", "Crear regla");

        return "reglas/crear_regla";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("reglaDTO") ReglaDTO reglaDTO,
                       @RequestParam("tipoPrestacion") List<String> tipoPrestacionIds) {
        List<NoutTipprs> tipoPrestaciones = noutTipprsService.findAllById(tipoPrestacionIds);

        super.service.save(reglaDTO.getRegla(), tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }

    @PostMapping("/save/{reglaId}")
    public String update(@PathVariable Long reglaId,
                         @ModelAttribute("reglaDTO") ReglaDTO reglaDTO,
                         @RequestParam("tipoPrestacion") List<String> tipoPrestacionIds) {

        List<NoutTipprs> tipoPrestaciones = noutTipprsService.findAllById(tipoPrestacionIds);
        super.service.updateAll(reglaId, reglaDTO.getRegla(), tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }


    //Otros

    @GetMapping("/validation")
    public String validate(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        PageDto<ReglaTipoPrestacionEntity> reglasTipoPrestacionPage = super.page(pageRequest);
        Iterable<NoutRegles> reglesPage = noutReglesService.findAll();

        model.addAttribute("reglasTipoPrestacion", reglasTipoPrestacionPage.getContent());
        model.addAttribute("reglas", reglesPage);
        model.addAttribute("titlePage", "Reglas ");
        return "reglas/validacion";
    }

    @GetMapping("/prestacions/{prestacionId}")
    public String prestacions(@PathVariable("prestacionId") Long prestacionId, Model model) {
        NoutTipprs tipoPrestacion = this.noutPrestacionsService
                .findById(prestacionId)
                .orElseThrow(EntityNotFoundException::new)
                .getTipoPrestacion();

        model.addAttribute("prestacion", tipoPrestacion);
        model.addAttribute("titlePage", "Tipus de prestació ");
        return "reglas/prestacion";
    }


}
