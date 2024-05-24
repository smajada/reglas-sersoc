package org.arteco.sersoc.controller;

import jakarta.persistence.EntityNotFoundException;
import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.dto.PageDTO;
import org.arteco.sersoc.model.entities.NoutSQLStatement;
import org.arteco.sersoc.repository.NoutSQLStatementRepository;
import org.arteco.sersoc.service.NoutSQLStatementService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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

        Pageable pageRequest = PageRequest.of(page, 2);
        PageDTO<NoutSQLStatement> sqlStatementPageDto2 = super.service.findByActiveTrue(pageRequest);

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
    public String saveSQLStatement(@ModelAttribute @Valid NoutSQLStatement sql, BindingResult bindingResult, Model model) {

        checkCustomErrors(sql, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("sql", sql);
            model.addAttribute("org.springframework.validation.BindingResult.sql", bindingResult);
            return "sql/crear_sentencia";
        }

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
                                     @ModelAttribute @Valid NoutSQLStatement sql,
                                     BindingResult bindingResult,
                                     Model model) {

        if (sql.getValue().isEmpty()) {
            bindingResult.addError(new FieldError("sql", "value", "El campo no puede estar vacío"));
        }

        if (bindingResult.hasErrors()) {
            NoutSQLStatement sqlStatement = this.service.findById(con).orElseThrow(EntityNotFoundException::new);

            model.addAttribute("sql", sqlStatement);
            model.addAttribute("org.springframework.validation.BindingResult.sql", bindingResult);
            model.addAttribute("titlePage", "Editar Sentencia");
            return "sql/editar_sentencia";
        }

        super.service.update(sql, con);

        return "redirect:/sql/list";
    }


    @GetMapping("/delete/{con}")
    public String deleteSQLStatement(@PathVariable("con") Long con) {

        NoutSQLStatement sql = this.service.findById(con).orElseThrow(EntityNotFoundException::new);

        this.service.delete(sql);

        return "redirect:/sql/list";
    }

    private void checkCustomErrors(NoutSQLStatement sql, BindingResult result) {
        Map<String, String> sqls = this.service.getAllSqlStatementByActive();
        for (Map.Entry<String, String> entry : sqls.entrySet()) {
            if (entry.getKey().equals(sql.getKey())) {
                result.rejectValue("key", "error.key", "La clave ya existe");
            }
        }

        if (sql.getValue().isEmpty()) {
            result.addError(new FieldError("sql", "value", "El campo no puede estar vacío"));
        }
    }
}
