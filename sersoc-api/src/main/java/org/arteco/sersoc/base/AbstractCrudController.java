package org.arteco.sersoc.base;

import com.fasterxml.jackson.annotation.JsonView;
import org.arteco.sersoc.dto.PageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import javax.swing.text.html.ListView;


public abstract class AbstractCrudController<
        ENTITY,
        ID,
        REPO extends JpaRepository<ENTITY, ID>,
        SERVICE extends AbstractCrudService<ENTITY, ID, REPO>>
        extends AbstractSecureController{

    protected final SERVICE service;

    protected AbstractCrudController(SERVICE service) {
        this.service = service;
    }

    @JsonView(ListView.class)
    @GetMapping("/page")
    public PageDto<ENTITY> page( final Pageable page) {
        return new PageDto<>(this.service.page(page));
    }
}
