package org.arteco.sersoc.base;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.arteco.sersoc.config.SecurityConfiguration;
import org.arteco.sersoc.dto.PageDTO;
import org.arteco.sersoc.dto.SaveErrorDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.ListView;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SecurityRequirements({
        @SecurityRequirement(name = SecurityConfiguration.BEARER_AUTH)
})
@Tag(name = "AbstractCrudController", description = "RestController")
public abstract class AbstractCrudController<
        ENTITY,
        ID,
        REPO extends JpaRepository<ENTITY, ID>,
        SERVICE extends AbstractCrudService<ENTITY, ID, REPO>>
        extends AbstractSecureController {

    protected final SERVICE service;

    protected AbstractCrudController(SERVICE service) {
        this.service = service;
    }

    @JsonView(ListView.class)
    @GetMapping("/page")
    public PageDTO<ENTITY> page(@Parameter(hidden = true) final Pageable page) {
        return new PageDTO<>(this.service.page(page));
    }

    @GetMapping("/list-all")
    @JsonView(ListView.class)
    @Parameter(name = "sort", description = "Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.",
            array = @ArraySchema(schema = @Schema(type = "string")))
    public Stream<ENTITY> list(@Parameter(hidden = true) final Sort sort) {
        return this.service.list(sort);
    }

    @PostMapping("/store")
    public ResponseEntity<ENTITY> save(@Valid @RequestBody final ENTITY entity,
                                       final BindingResult bindingResult) throws BindingResultException {
        if (bindingResult.hasErrors()) {
            List<SaveErrorDTO> errors = bindingResult.getAllErrors().stream()
                    .map(SaveErrorDTO::new)
                    .toList();
            throw new BindingResultException(errors);
        }
        final ENTITY result = this.service.save(entity);
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<ENTITY> getById(ID id) {
        final Optional<ENTITY> opt = this.service.findById(id);
        return ResponseEntity.of(opt);
    }

    protected ResponseEntity<Boolean> deleteOrDismissById(ID id) {
        this.service.findById(id).ifPresent(this.service::deleteOrDismiss);
        return ResponseEntity.ok(true);
    }
}
