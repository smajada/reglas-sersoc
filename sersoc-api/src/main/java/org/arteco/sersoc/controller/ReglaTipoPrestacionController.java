package org.arteco.sersoc.controller;

import jakarta.persistence.EntityNotFoundException;
import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.dto.PageDto;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
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

@Controller
@RequestMapping("/regla-tipo-prestacion")
public class ReglaTipoPrestacionController extends AbstractCrudController<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository, ReglaTipoPrestacionService> {

    private final NoutTipprsService noutTipprsService;
    private final NoutReglesService noutReglesService;
    private final ReglaTipoPrestacionService reglaTipoPrestacionService;
    private final NoutReglesController noutReglesController;

    protected ReglaTipoPrestacionController(ReglaTipoPrestacionService service, NoutTipprsService noutTipprsService, NoutReglesService noutReglesService, ReglaTipoPrestacionService reglaTipoPrestacionService) {
        super(service);
        this.noutTipprsService = noutTipprsService;
        this.noutReglesService = noutReglesService;
        this.reglaTipoPrestacionService = reglaTipoPrestacionService;
        this.noutReglesController = new NoutReglesController(noutReglesService);
    }

    @GetMapping("/list")
    public String listAllReglasTipoPrestacion(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        PageDto<ReglaTipoPrestacionEntity> reglasTipoPrestacionPage = super.page(pageRequest);
        PageDto<NoutRegles> reglesPage = this.noutReglesController.page(pageRequest);

        model.addAttribute("totalPages", reglesPage.getTotalPages());
        model.addAttribute("reglasTipoPrestacion", reglasTipoPrestacionPage.getContent());
        model.addAttribute("reglas", reglesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("titlePage", "Reglas ");
        return "reglas/reglas";
    }


    @GetMapping("/editar/{reglaId}")
    public String editarReglasTipoPrestacion(@PathVariable("reglaId") Long reglaId, Model model) {
        NoutRegles regla = this.noutReglesService.findById(reglaId).orElseThrow(EntityNotFoundException::new);

        List<NoutTipprs> allTipoPrestacion = (List<NoutTipprs>) noutTipprsService.findAll();

        List<ReglaTipoPrestacionEntity> allReglasTipoPrestacion = (List<ReglaTipoPrestacionEntity>) super.service.findAll();

        List<String> reglasTipoPrestacionList = new ArrayList<>();
        for (ReglaTipoPrestacionEntity reglaTipoPrestacion : allReglasTipoPrestacion) {
            if (reglaTipoPrestacion.getNoutRegles().getCon().equals(regla.getCon()) && reglaTipoPrestacion.getActive()) {
                reglasTipoPrestacionList.add(reglaTipoPrestacion.getNoutTipprs().getCoa());
            }
        }

        ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();

        model.addAttribute("reglaTipoPrestacion", reglaTipoPrestacion);
        model.addAttribute("regla", regla);
        model.addAttribute("allTipoPrestacion", allTipoPrestacion);
        model.addAttribute("reglasTipoPrestacionId", reglasTipoPrestacionList);
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
    public String crearReglasTipoPrestacion(Model model) {
        NoutRegles regla = new NoutRegles();
        model.addAttribute("regla", regla);

        List<NoutTipprs> tipoPrestaciones = new ArrayList<>();
        model.addAttribute("tipoPrestaciones", tipoPrestaciones);

        List<NoutTipprs> tipoPrestacionList = (List<NoutTipprs>) noutTipprsService.findAll();
        model.addAttribute("tipoPrestacionList", tipoPrestacionList);

        model.addAttribute("titlePage", "Crear regla");
        return "reglas/crear_regla";
    }

    @PostMapping("/save")
    public String saveReglaTipoPrestacion(@ModelAttribute NoutRegles regla,
                                          @RequestParam("tipoPrestacion") List<String> tipoPrestacionIds) {
        List<NoutTipprs> tipoPrestaciones = noutTipprsService.findAllById(tipoPrestacionIds);
        super.service.saveReglaWithTipoPrestacion(regla, tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }

    @PostMapping("/save/{reglaId}")
    public String updateReglaTipoPrestacion(@PathVariable Long reglaId,
                                            @ModelAttribute NoutRegles regla,
                                            @RequestParam("tipoPrestacion") List<String> tipoPrestacionIds) {

        List<NoutTipprs> tipoPrestaciones = noutTipprsService.findAllById(tipoPrestacionIds);
        super.service.updateReglaWithTipoPrestacion(reglaId, regla, tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }

    private ReglasTipoPrestacionId buildId(Long reglaId, String tipoPrestacionId) {
        ReglasTipoPrestacionId id = new ReglasTipoPrestacionId();
        id.setReglaId(reglaId);
        id.setTipoPrestacionId(tipoPrestacionId);
        return id;
    }


}
