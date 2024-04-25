package org.arteco.sersoc.controller;

import jakarta.persistence.EntityNotFoundException;
import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.dto.*;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/regla-tipo-prestacion")
public class ReglaTipoPrestacionController extends AbstractCrudController<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository, ReglaTipoPrestacionService> {

    private final NoutTipprsService noutTipprsService;
    private final NoutReglesService noutReglesService;
    private final NoutReglesController noutReglesController;
    private final NoutPrestacionsService noutPrestacionsService;
    private final NashornService nashornService;

    public ReglaTipoPrestacionController(ReglaTipoPrestacionService service, NoutTipprsService noutTipprsService, NoutReglesService noutReglesService, NoutReglesController noutReglesController, NoutPrestacionsService noutPrestacionsService, NashornService nashornService) {
        super(service);
        this.noutTipprsService = noutTipprsService;
        this.noutReglesService = noutReglesService;
        this.noutReglesController = noutReglesController;
        this.noutPrestacionsService = noutPrestacionsService;
        this.nashornService = nashornService;
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

        List<String> reglasTipoPrestacionList = allReglasTipoPrestacion.stream().filter(reglaTipoPrestacion -> reglaTipoPrestacion.getNoutRegles().getCon().equals(regla.getCon()) && reglaTipoPrestacion.getActive()).map(reglaTipoPrestacion -> reglaTipoPrestacion.getNoutTipprs().getCoa()).collect(Collectors.toList());

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
    public String save(@ModelAttribute("reglaDTO") ReglaDTO reglaDTO, @RequestParam("tipoPrestacion") List<String> tipoPrestacionIds) {
        List<NoutTipprs> tipoPrestaciones = noutTipprsService.findAllById(tipoPrestacionIds);

        super.service.save(reglaDTO.getRegla(), tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }

    @PostMapping("/save/{reglaId}")
    public String update(@PathVariable Long reglaId, @ModelAttribute("reglaDTO") ReglaDTO reglaDTO, @RequestParam("tipoPrestacion") List<String> tipoPrestacionIds) {

        List<NoutTipprs> tipoPrestaciones = noutTipprsService.findAllById(tipoPrestacionIds);
        super.service.updateAll(reglaId, reglaDTO.getRegla(), tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }


    //Otros

    //Template resultado validación
    @GetMapping("/validation/{prestacionTipID}/{prestacionID}")
    public String validate(Model model, @PathVariable String prestacionTipID, @PathVariable Long prestacionID) {
        GenericValidationDTO genericValidationDTO = validateTipoPrestacion(prestacionTipID, prestacionID);

        if (genericValidationDTO.getError().isEmpty()) {
            model.addAttribute("titlePage", "Validación correcta.");
        } else {
            model.addAttribute("titlePage", "Prestación no validada.");
        }

        model.addAttribute("genericValidationDTO", genericValidationDTO);

        return "reglas/validacion";
    }

    //Template de prestación
    @GetMapping("/prestacions/{prestacionId}")
    public String getPrestacion(@PathVariable("prestacionId") Long prestacionId, Model model) {
        NoutPrestacions prestacion = this.noutPrestacionsService.findById(prestacionId).orElseThrow(EntityNotFoundException::new);

        NoutTipprs tipoPrestacion = prestacion.getTipoPrestacion();

        model.addAttribute("tipoPrestacion", tipoPrestacion);
        model.addAttribute("prestacion", prestacion);
        model.addAttribute("titlePage", "Tipus de prestació ");
        return "reglas/prestacion";
    }

    private GenericValidationDTO validateTipoPrestacion(String prestacionTipoID, Long prestacionID) {

        List<NoutRegles> reglas = noutReglesService.findByIdTipoPrestacion(prestacionTipoID);
        NoutPrestacions prestacion = noutPrestacionsService.findById(prestacionID).orElseThrow(EntityNotFoundException::new);

        //Acceder a la fecha de ahora
        Date now = new Date(System.currentTimeMillis());
        GenericValidationDTO genericValidationDTO = new GenericValidationDTO();

        try {
            //Serializa el objeto prestacion a JSON con ObjectMapper
            String prestacionJson = nashornService.serializePrestacionToJson(prestacion);

            //Añadir al contexto de Nashorn el objeto json
            //TODO: Formato fecha
            nashornService.putInContext("prestacion", prestacionJson);
            nashornService.executeScript("var prestacion = JSON.parse(prestacion);");
            nashornService.executeScript("var datIni = new Date(prestacion.datIni);");
            nashornService.executeScript("var datFin = new Date(prestacion.datFin);");
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (NoutRegles regla : reglas) {
            String script = regla.getScript();

            try {
                Object result = nashornService.executeScript(script);

                if (Boolean.TRUE.equals(result)) {
                    if (this.numDias(now, regla.getDatFin())) {
                        ValidationWarningDTO warningDTO = new ValidationWarningDTO();
                        warningDTO.setReglaId(regla.getCon());
                        warningDTO.setDescription(regla.getDec());
                        genericValidationDTO.getWarning().add(warningDTO);
                    } else {
                        ValidationSuccessDTO validationSuccessDTO = new ValidationSuccessDTO();
                        validationSuccessDTO.setReglaId(regla.getCon());
                        validationSuccessDTO.setDescription(regla.getDec());
                        genericValidationDTO.getSuccess().add(validationSuccessDTO);
                    }

                } else {
                    ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();
                    validationErrorDTO.setReglaId(regla.getCon());
                    validationErrorDTO.setDescription(regla.getDec());
                    genericValidationDTO.getError().add(validationErrorDTO);
                }

            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }

        return genericValidationDTO;
    }

    private Boolean numDias(Date date1, Date date2) {
        // Calcular la diferencia en milisegundos
        long diferenciaMilisegundos = Math.abs(date2.getTime() - date1.getTime());

        // Convertir la diferencia de milisegundos a días
        long diferenciaDias = diferenciaMilisegundos / (1000 * 60 * 60 * 24);

        // Devolver true si la diferencia es menor que 7 días
        return diferenciaDias < 7;
    }
}
