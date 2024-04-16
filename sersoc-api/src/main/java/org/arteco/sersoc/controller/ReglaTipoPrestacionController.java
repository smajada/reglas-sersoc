package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.dto.PageDto;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaEntity;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.model.entities.TipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;
import org.arteco.sersoc.service.TipoPrestacionService;
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

    private final TipoPrestacionService tipoPrestacionService;

    protected ReglaTipoPrestacionController(ReglaTipoPrestacionService service, TipoPrestacionService tipoPrestacionService) {
        super(service);
        this.tipoPrestacionService = tipoPrestacionService;
    }

    @GetMapping("/list")
    public String listAllReglasTipoPrestacion(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        PageDto<ReglaTipoPrestacionEntity> reglasTipoPrestacionPage = super.page(pageRequest);
//        System.out.println(reglasTipoPrestacionPage.getContent());
        model.addAttribute("totalPages", reglasTipoPrestacionPage.getTotalPages());
        model.addAttribute("reglasTipoPrestacion", reglasTipoPrestacionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("titlePage", "Reglas ");
        return "reglas/reglas";
    }



    @GetMapping("/editar/{reglaId}/{tipoPrestacionId}")
    public String editarReglasTipoPrestacion(@PathVariable("reglaId") Long reglaId, @PathVariable("tipoPrestacionId") Long tipoPrestacionId, Model model) {
        ReglasTipoPrestacionId id = buildId(reglaId, tipoPrestacionId);

        ReglaTipoPrestacionEntity reglaTipoPrestacion = service.findById(id).get();
        model.addAttribute("reglaTipoPrestacion", reglaTipoPrestacion);

        List<TipoPrestacionEntity> tipoPrestacionList = (List<TipoPrestacionEntity>) tipoPrestacionService.findAll();
        model.addAttribute("tipoPrestacionList", tipoPrestacionList);

        model.addAttribute("titlePage", "Editar regla");
        return "reglas/editar_regla";
    }

    @GetMapping("/delete/{reglaId}/{tipoPrestacionId}")
    public String delete(@PathVariable("reglaId") Long reglaId, @PathVariable("tipoPrestacionId") Long tipoPrestacionId) {
        ReglasTipoPrestacionId id = buildId(reglaId, tipoPrestacionId);

        ReglaTipoPrestacionEntity reglaTipoPrestacion = service.findById(id).get();

        service.delete(reglaTipoPrestacion);
        System.out.println(reglaTipoPrestacion.getReglaEntity().isActive());
        return "redirect:/regla-tipo-prestacion/list";
    }


    @GetMapping("/crear")
    public String crearReglasTipoPrestacion(Model model) {
        ReglaEntity regla = new ReglaEntity();
        model.addAttribute("regla", regla);

        List<TipoPrestacionEntity> tipoPrestaciones = new ArrayList<>();
        model.addAttribute("tipoPrestaciones", tipoPrestaciones);

        List<TipoPrestacionEntity> tipoPrestacionList = (List<TipoPrestacionEntity>) tipoPrestacionService.findAll();
        model.addAttribute("tipoPrestacionList", tipoPrestacionList);

        model.addAttribute("titlePage", "Crear regla");
        return "reglas/crear_regla";
    }

    @PostMapping("/save")
    public String saveReglaTipoPrestacion(@ModelAttribute ReglaEntity regla, @RequestParam("tipoPrestacion") List<Long> tipoPrestacionIds) {
        List<TipoPrestacionEntity> tipoPrestaciones = tipoPrestacionService.findAllById(tipoPrestacionIds);

        super.service.saveReglaWithTipoPrestacion(regla, tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }


    private ReglasTipoPrestacionId buildId(Long reglaId, Long tipoPrestacionId) {
        ReglasTipoPrestacionId id = new ReglasTipoPrestacionId();
        id.setReglaId(reglaId);
        id.setTipoPrestacionId(tipoPrestacionId);
        return id;
    }


}
