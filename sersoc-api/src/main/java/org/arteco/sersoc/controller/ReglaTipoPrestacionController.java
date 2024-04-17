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

    protected ReglaTipoPrestacionController(ReglaTipoPrestacionService service, NoutTipprsService noutTipprsService, NoutReglesService noutReglesService, ReglaTipoPrestacionService reglaTipoPrestacionService) {
        super(service);
        this.noutTipprsService = noutTipprsService;
        this.noutReglesService = noutReglesService;
        this.reglaTipoPrestacionService = reglaTipoPrestacionService;
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
    public String editarReglasTipoPrestacion(@PathVariable("reglaId") Long reglaId, @PathVariable("tipoPrestacionId") String tipoPrestacionId, Model model) {
        ReglasTipoPrestacionId id = buildId(reglaId, tipoPrestacionId);

        ReglaTipoPrestacionEntity reglaTipoPrestacion = service.findById(id).get();
        model.addAttribute("reglaTipoPrestacion", reglaTipoPrestacion);

        List<NoutTipprs> tipoPrestacionList = (List<NoutTipprs>) noutTipprsService.findAll();
        model.addAttribute("tipoPrestacionList", tipoPrestacionList);
        model.addAttribute("titlePage", "Editar regla");
        return "reglas/editar_regla";
    }

    @GetMapping("/delete/{reglaId}/{tipoPrestacionId}")
    public String delete(@PathVariable("reglaId") Long reglaId,
                         @PathVariable("tipoPrestacionId") String tipoPrestacionId) {
        ReglasTipoPrestacionId id = buildId(reglaId, tipoPrestacionId);

        ReglaTipoPrestacionEntity reglaTipoPrestacion = service.findById(id).get();

        service.delete(reglaTipoPrestacion);
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


    @PostMapping("/update/{reglaId}/{tipoPrestacionId}")
    public String updateReglaTipoPrestacion(@PathVariable Long reglaId, @PathVariable String tipoPrestacionId, @ModelAttribute("reglaTipoPrestacion") ReglaTipoPrestacionEntity updatedReglaTipoPrestacion) {
        ReglasTipoPrestacionId id = buildId(reglaId, tipoPrestacionId);
        // Obtener la entidad existente de la base de datos

        ReglasTipoPrestacionId updatedId = buildId(reglaId, updatedReglaTipoPrestacion.getNoutTipprs().getCoa());

        if (service.findById(updatedId).isPresent()) {
            ReglaTipoPrestacionEntity existingReglaTipoPrestacion = service.findById(id).get();

            // Actualizar los campos relevantes
            existingReglaTipoPrestacion.getNoutRegles().setDec(updatedReglaTipoPrestacion.getNoutRegles().getDec());
            existingReglaTipoPrestacion.getNoutRegles().setDatIni(updatedReglaTipoPrestacion.getNoutRegles().getDatIni());
            existingReglaTipoPrestacion.getNoutRegles().setDatFin(updatedReglaTipoPrestacion.getNoutRegles().getDatFin());
            existingReglaTipoPrestacion.getNoutRegles().setScript(updatedReglaTipoPrestacion.getNoutRegles().getScript());

            // Guardar los cambios
            service.save(existingReglaTipoPrestacion);
        } else {
            reglaTipoPrestacionService.findById(id).ifPresent(reglaTipoPrestacionService::delete);

            this.reglaTipoPrestacionService.saveSingleReglaWithTipoPrestacion(updatedReglaTipoPrestacion.getNoutTipprs().getCoa(), reglaId);
        }

        return "redirect:/regla-tipo-prestacion/list";
    }

    private ReglasTipoPrestacionId buildId(Long reglaId, String tipoPrestacionId) {
        ReglasTipoPrestacionId id = new ReglasTipoPrestacionId();
        id.setReglaId(reglaId);
        id.setTipoPrestacionId(tipoPrestacionId);
        return id;
    }


}
