package org.arteco.sersoc.controller;

import jakarta.persistence.EntityNotFoundException;
import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.dto.PageDto;
import org.arteco.sersoc.model.entities.NoutSQLStatement;
import org.arteco.sersoc.repository.NoutSQLStatementRepository;
import org.arteco.sersoc.service.NoutSQLStatementService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sql")
public class NoutSQLStatementController extends AbstractCrudController<
        NoutSQLStatement,
        Long,
        NoutSQLStatementRepository,
        NoutSQLStatementService> {

    protected NoutSQLStatementController(NoutSQLStatementService service) {
        super(service);
    }

    @GetMapping("/list")
    public String ListSqlStatement(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

        Pageable pageRequest = PageRequest.of(page, 1);
        PageDto<NoutSQLStatement> sqlStatementPageDto2 = super.service.findByActiveTrue(pageRequest);

        model.addAttribute("totalPages", sqlStatementPageDto2.getTotalPages());
        model.addAttribute("sentences", sqlStatementPageDto2.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("titlePage", "SQL ");

        return "sql/sql_statement";
    }

    @GetMapping("/crear")
    public String createSqlSentence(Model model) {

        NoutSQLStatement sql = new NoutSQLStatement();
        model.addAttribute("sql", sql);

        model.addAttribute("titlePage", "Crear Sentencia");
        return "sql/crear_sentencia";
    }

    @PostMapping("/save")
    public String saveSQLStatement(@ModelAttribute NoutSQLStatement sql) {

        super.service.save(sql);
        return "redirect:/sql/list";
    }

    @GetMapping("/editar/{con}")
    public String editSQLStatement(@PathVariable("con") Long con, Model model) {

        NoutSQLStatement sql = this.service.findById(con).orElseThrow(EntityNotFoundException::new);

        model.addAttribute("sql", sql);
        model.addAttribute("titlePage", "Editar Sentencia");

        return "sql/editar_sentencia";
    }

    @PostMapping("/save/{con}")
    public String updateSQLStatement(@PathVariable Long con,
                                     @ModelAttribute NoutSQLStatement sql) {

        super.service.update(sql, con);

        return "redirect:/sql/list";
    }

    @GetMapping("/delete/{con}")
    public String deleteSQLStatement(@PathVariable("con") Long con) {

        NoutSQLStatement sql = this.service.findById(con).orElseThrow(EntityNotFoundException::new);

        this.service.delete(sql);

        return "redirect:/sql/list";
    }
}
