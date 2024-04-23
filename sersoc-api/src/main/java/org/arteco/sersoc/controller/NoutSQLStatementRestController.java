package org.arteco.sersoc.controller;

import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.model.entities.NoutSQLStatement;
import org.arteco.sersoc.repository.NoutSQLStatementRepository;
import org.arteco.sersoc.service.NoutSQLStatementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sql")
public class NoutSQLStatementRestController extends AbstractCrudController<
        NoutSQLStatement,
        Long,
        NoutSQLStatementRepository,
        NoutSQLStatementService> {

    protected NoutSQLStatementRestController(NoutSQLStatementService service) { super(service); }

    @PostMapping("/save")
    public void saveNoutSQLStatement(@RequestBody NoutSQLStatement noutSQLStatement) {
        service.save(noutSQLStatement);
    }

    @GetMapping("/all")
    public List<NoutSQLStatement> all() {
        return (List<NoutSQLStatement>) service.findAll();
    }

    @GetMapping("/map")
    public Map<String, String> allMapKey() {
        return service.getAllSqlStatement();
    }

    @GetMapping("/map_active")
    public Map<String, String> allMapKeyActive() {
        return service.getAllSqlStatementByActive();
    }
}
