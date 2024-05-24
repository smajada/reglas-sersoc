package org.arteco.sersoc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.arteco.sersoc.base.AbstractCrudController;
import org.arteco.sersoc.config.SecurityConfiguration;
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


    @Operation(summary = "Save sql endpoint" , description = "Save SQL endpoint to check if the API is working properly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden - API key is missing or invalid")
    })
    @PostMapping("/save")
    public void saveNoutSQLStatement(@RequestBody NoutSQLStatement noutSQLStatement) {
        service.save(noutSQLStatement);
    }

    @Operation(summary = "all sql endpoint" , description = "SQL endpoint to check if the API is working properly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden - API key is missing or invalid")
    })
    @GetMapping("/all")
    public List<NoutSQLStatement> all() {
        return (List<NoutSQLStatement>) service.findAll();
    }

    @Operation(summary = "allMapKey sql endpoint" , description = "AllMapKey SQL endpoint to check if the API is working properly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden - API key is missing or invalid")
    })
    @GetMapping("/map")
    public Map<String, String> allMapKey() {
        return service.getAllSqlStatement();
    }

    @Operation(summary = "allMapKeyActive sql endpoint" , description = "allMapKeyActive SQL endpoint to check if the API is working properly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden - API key is missing or invalid")
    })
    @GetMapping("/map_active")
    public Map<String, String> allMapKeyActive() {
        return service.getAllSqlStatementByActive();
    }
}
