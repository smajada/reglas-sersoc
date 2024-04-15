package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.model.entities.TipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;
import org.arteco.sersoc.service.TipoPrestacionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/regla-tipo-prestacion")
public class ReglaTipoPrestacionController extends AbstractCrudController<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository, ReglaTipoPrestacionService> {

    private final TipoPrestacionService tipoPrestacionService;

    protected ReglaTipoPrestacionController(ReglaTipoPrestacionService service, TipoPrestacionService tipoPrestacionService) {
        super(service);
        this.tipoPrestacionService = tipoPrestacionService;
    }

    @GetMapping("/list")
    public String listAllReglasTipoPrestacion(
            Model model,
            @Param("keyword") String keyword,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size)

    {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        // Se obtiene la lista de reglas de tipo prestación paginada y filtrada por nombre
        Page<ReglaTipoPrestacionEntity> reglasTipoPrestacionPage = service.findPaginated(PageRequest.of(currentPage - 1, pageSize), keyword);

        int totalPages = reglasTipoPrestacionPage.getTotalPages();
        // Si el total de páginas es mayor a 0, se crea una lista con los números de las páginas
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Se añaden los datos a la vista
        model.addAttribute("reglasTipoPrestacionPage", reglasTipoPrestacionPage);
        model.addAttribute("keyword", keyword);
        return  "reglas/reglas";
    }

    @GetMapping("/crear")
    public String crearReglasTipoPrestacion(Model model){
        ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
        model.addAttribute("reglaTipoPrestacion", reglaTipoPrestacion);
        List<TipoPrestacionEntity> tipoPrestacionList = (List<TipoPrestacionEntity>) tipoPrestacionService.findAll();
        model.addAttribute("tipoPrestacionList", tipoPrestacionList);

        model.addAttribute("titlePage", "Nueva regla tipo prestacion");
        return "reglas/crear_regla";
    }

//    @GetMapping("/editar/{reglaId}/{tipoPrestacionId}")
//    public String editarReglasTipoPrestacion(Model model, @PathVariable(value = "reglaId") Long reglaId, @PathVariable(value = "tipoPrestacionId") Long tipoPrestacionId){
//        ReglasTipoPrestacionId id = new ReglasTipoPrestacionId();
//        id.setReglaId(reglaId);
//        id.setTipoPrestacionId(tipoPrestacionId);
//
//        ReglaTipoPrestacionEntity reglaTipoPrestacion = service.findById(id).get();
//        List<TipoPrestacionEntity> tipoPrestacionList = (List<TipoPrestacionEntity>) tipoPrestacionService.findAll();
//        model.addAttribute("reglaTipoPrestacion", reglaTipoPrestacion);
//        model.addAttribute("tipoPrestacionList", tipoPrestacionList);
//        model.addAttribute("titlePage", "Editar regla tipo prestacion");
//        return "reglas/crear_regla";
//    }


}
