package org.arteco.sersoc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.dto.PageDTO;
import org.arteco.sersoc.dto.ReglaDTO;
import org.arteco.sersoc.dto.validation.DateValidationDTO;
import org.arteco.sersoc.dto.validation.GenericValidationDTO;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.service.*;
import org.arteco.sersoc.utils.AuthenticateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/regla-tipo-prestacion")
public class ReglaTipoPrestacionController extends AbstractCrudController<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository, ReglaTipoPrestacionService> {

    private static final Logger logger = LoggerFactory.getLogger(ReglaTipoPrestacionController.class);
    private final NoutTipprsService noutTipprsService;
    private final NoutReglesService noutReglesService;
    private final NoutReglesController noutReglesController;
    private final NoutPrestacionsService noutPrestacionsService;
    private final NashornService nashornService;
    private final DataService dataService;
    private final NoutSQLStatementService noutSQLStatementService;


    public ReglaTipoPrestacionController(ReglaTipoPrestacionService service, NoutTipprsService noutTipprsService, NoutReglesService noutReglesService, NoutReglesController noutReglesController, NoutPrestacionsService noutPrestacionsService, NashornService nashornService, DataService dataService, NoutSQLStatementService noutSQLStatementService) {
        super(service);
        this.noutTipprsService = noutTipprsService;
        this.noutReglesService = noutReglesService;
        this.noutReglesController = noutReglesController;
        this.noutPrestacionsService = noutPrestacionsService;
        this.nashornService = nashornService;
        this.dataService = dataService;
        this.noutSQLStatementService = noutSQLStatementService;
    }


    @GetMapping("/list")
    public String listAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        AuthenticateUtils.addAuthenticatedAttribute(model);
        Pageable pageRequest = PageRequest.of(page, 10);
        Iterable<ReglaTipoPrestacionEntity> reglasTipoPrestacionPage = super.service.findAll();
        PageDTO<NoutRegles> reglesPage = noutReglesController.page(pageRequest);

        model.addAttribute("reglas", reglesPage);
        model.addAttribute("reglasTipoPrestacion", reglasTipoPrestacionPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("titlePage", "Reglas ");
        return "reglas/reglas";
    }

    @GetMapping("/editar/{reglaId}")
    public String edit(@PathVariable("reglaId") Long reglaId, Model model) {
        AuthenticateUtils.addAuthenticatedAttribute(model);
        NoutRegles regla = this.noutReglesService.findById(reglaId).orElseThrow(EntityNotFoundException::new);

        List<NoutTipprs> allTipoPrestacion = (List<NoutTipprs>) noutTipprsService.findAll();

        List<ReglaTipoPrestacionEntity> allReglasTipoPrestacion = (List<ReglaTipoPrestacionEntity>) super.service.findAll();

        List<String> reglasTipoPrestacionList = allReglasTipoPrestacion
                .stream()
                .filter(reglaTipoPrestacion -> reglaTipoPrestacion.getNoutRegles().getCon().equals(regla.getCon()) && reglaTipoPrestacion.getActive())
                .map(reglaTipoPrestacion -> reglaTipoPrestacion.getNoutTipprs().getCoa())
                .toList();

        ReglaDTO reglaDTO = new ReglaDTO();
        reglaDTO.setCon(regla.getCon());
        reglaDTO.setDec(regla.getDec());
        reglaDTO.setDatIni(regla.getDatIni());
        reglaDTO.setDatFin(regla.getDatFin());
        reglaDTO.setScript(regla.getScript());
        reglaDTO.setAllTipoPrestacion(allTipoPrestacion);
        // Establecer el valor seleccionado en el select
        reglaDTO.setTipoPrestacionSelected(reglasTipoPrestacionList);

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
        AuthenticateUtils.addAuthenticatedAttribute(model);
        ReglaDTO reglaDTO = new ReglaDTO();
        reglaDTO.setAllTipoPrestacion(new ArrayList<>());

        Iterable<NoutTipprs> tipoPrestacionList = noutTipprsService.findAll();

        model.addAttribute("reglaDTO", reglaDTO);
        model.addAttribute("tipoPrestacionList", tipoPrestacionList);
        model.addAttribute("titlePage", "Crear regla");

        return "reglas/crear_regla";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute("reglaDTO") @Valid ReglaDTO reglaDTO,
                       BindingResult bindingResult,
                       @RequestParam("tipoPrestacion") List<String> tipoPrestacionIds,
                       Model model) {

        // Verificar si las fechas son válidas
        checkDate(reglaDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            Iterable<NoutTipprs> tipoPrestacionList = noutTipprsService.findAll();

            model.addAttribute("tipoPrestacionList", tipoPrestacionList);
            return "reglas/crear_regla";
        }

        List<NoutTipprs> tipoPrestaciones = noutTipprsService.findAllById(tipoPrestacionIds);

        NoutRegles regla = new NoutRegles();
        regla.setDec(reglaDTO.getDec());
        regla.setDatIni(reglaDTO.getDatIni());
        regla.setDatFin(reglaDTO.getDatFin());
        regla.setScript(reglaDTO.getScript());

        super.service.save(regla, tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }

    @PostMapping("/save/{reglaId}")
    public String update(@PathVariable Long reglaId,
                         @ModelAttribute("reglaDTO") @Valid ReglaDTO reglaDTO,
                         BindingResult bindingResult,
                         @RequestParam("tipoPrestacion") List<String> tipoPrestacionIds,
                         Model model) {
        // Verificar si las fechas son válidas
        checkDate(reglaDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            List<NoutTipprs> allTipoPrestacion = (List<NoutTipprs>) noutTipprsService.findAll();

            reglaDTO.setCon(reglaId);
            reglaDTO.setAllTipoPrestacion(allTipoPrestacion);
            reglaDTO.setTipoPrestacionSelected(tipoPrestacionIds);

            model.addAttribute("reglaDTO", reglaDTO);
            model.addAttribute("titlePage", "Editar regla");

            return "reglas/editar_regla";
        }

        List<NoutTipprs> tipoPrestaciones = noutTipprsService.findAllById(tipoPrestacionIds);
        NoutRegles regla = new NoutRegles();
        regla.setCon(reglaId);
        regla.setDec(reglaDTO.getDec());
        regla.setDatIni(reglaDTO.getDatIni());
        regla.setDatFin(reglaDTO.getDatFin());
        regla.setScript(reglaDTO.getScript());

        super.service.updateAll(reglaId, regla, tipoPrestaciones);

        return "redirect:/regla-tipo-prestacion/list";
    }

    //Template resultado validación
    @GetMapping("/validation/{prestacionTipID}/{prestacionID}")
    public String validate(Model model, @PathVariable String prestacionTipID, @PathVariable Long prestacionID) {
        AuthenticateUtils.addAuthenticatedAttribute(model);
        List<GenericValidationDTO> genericValidationDTO = validateTipoPrestacion(prestacionTipID, prestacionID, noutReglesService, noutPrestacionsService);

        List<GenericValidationDTO> validationError = genericValidationDTO.stream()
                .filter(validation -> validation.getType().equals("ERROR"))
                .toList();
        List<GenericValidationDTO> validationWarning = genericValidationDTO.stream().filter(validation -> validation.getType().equals("WARNING")).toList();
        List<GenericValidationDTO> validationSuccess = genericValidationDTO.stream().filter(validation -> validation.getType().equals("SUCCESS")).toList();


        model.addAttribute("titlePage", "Validación de prestación");
        model.addAttribute("validationError", validationError);
        model.addAttribute("validationWarning", validationWarning);
        model.addAttribute("validationSuccess", validationSuccess);

        return "reglas/validacion";
    }

    //Template de prestación
    @GetMapping("/prestacions/{prestacionId}")
    public String getPrestacion(@PathVariable("prestacionId") Long prestacionId, Model model) {
        AuthenticateUtils.addAuthenticatedAttribute(model);
        NoutPrestacions prestacion = this.noutPrestacionsService.findById(prestacionId).orElseThrow(EntityNotFoundException::new);

        NoutTipprs tipoPrestacion = prestacion.getTipoPrestacion();

        model.addAttribute("tipoPrestacion", tipoPrestacion);
        model.addAttribute("prestacion", prestacion);
        model.addAttribute("titlePage", "Tipus de prestació ");
        return "reglas/prestacion";
    }

    //Métodos privados
    private List<GenericValidationDTO> validateTipoPrestacion(String prestacionTipoID, Long prestacionID, NoutReglesService noutReglesService, NoutPrestacionsService noutPrestacionsService) {
        List<NoutRegles> reglas = noutReglesService.findByIdTipoPrestacion(prestacionTipoID);
        NoutPrestacions prestacion = noutPrestacionsService.findById(prestacionID).orElseThrow(EntityNotFoundException::new);
        List<GenericValidationDTO> validationList = new ArrayList<>();

        Date now = new Date(System.currentTimeMillis());

        try {
            nashornInitialConfig(prestacion);
        } catch (Exception e) {
            logger.error("An error occurred while initializing Nashorn", e);
        }

        for (NoutRegles regla : reglas) {
            String script = regla.getScript();

            // Validación de fecha de caducidad de la regla
            GenericValidationDTO dateValidation = DateValidationDTO.checkExpireDate(regla, now);

            if (dateValidation != null) {
                validationList.add(dateValidation);
            }

            try {
                GenericValidationDTO validationResults = (GenericValidationDTO) nashornService.executeScript(script);
                GenericValidationDTO genericValidationDTO = new GenericValidationDTO();
                genericValidationDTO.setMessage(validationResults.getMessage());
                genericValidationDTO.setType(validationResults.getType());

                validationList.add(genericValidationDTO);

            } catch (ScriptException e) {
                logger.error("An error occurred while executing the script", e);
            }
        }

        return validationList;
    }

    private void nashornInitialConfig(NoutPrestacions prestacion) throws JsonProcessingException, ScriptException {

        // Serializa el objeto prestacion a JSON con ObjectMapper
        String prestacionJson = nashornService.serializeObjectToJson(prestacion);
        Map<String, String> sentencias = noutSQLStatementService.getAllSqlStatementByActive();
        ObjectMapper objectMapper = new ObjectMapper();
        String sentenciasJson = objectMapper.writeValueAsString(sentencias);
        GenericValidationDTO genericValidationDTO = new GenericValidationDTO();

        nashornService.putInContext("DataService", this.dataService);
        nashornService.putInContext("prestacion", prestacionJson);
        nashornService.putInContext("sentencias", sentenciasJson);
        nashornService.putInContext("validacion", genericValidationDTO);

        // Script of initialization
        String script = "var prestacion = JSON.parse(prestacion);"
                + "var datIni = new Date(prestacion.datIni);"
                + "var datFin = new Date(prestacion.datFin);"
                + "var sentencias = JSON.parse(sentencias);"
                + "var dataService = DataService;"
                + "var validacion = validacion;";

        nashornService.executeScript(script);
    }

    private void checkDate(ReglaDTO reglaDTO, BindingResult bindingResult) {
        if (reglaDTO.getDatIni().after(reglaDTO.getDatFin())) {
            bindingResult.rejectValue("datIni", "error.reglaDTO", "La fecha de inicio debe ser anterior a la fecha de finalización");
        }
    }
}
