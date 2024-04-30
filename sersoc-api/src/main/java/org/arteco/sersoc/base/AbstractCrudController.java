package org.arteco.sersoc.base;

import com.fasterxml.jackson.annotation.JsonView;
import org.arteco.sersoc.dto.PageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

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
    public PageDTO<ENTITY> page(final Pageable page) {
        return new PageDTO<>(this.service.page(page));
    }
}
