package org.arteco.sersoc.base;

import org.arteco.sersoc.dto.PageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;


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


    public PageDto<ENTITY> page( final Pageable page) {
        return new PageDto<>(this.service.page(page));
    }
}
