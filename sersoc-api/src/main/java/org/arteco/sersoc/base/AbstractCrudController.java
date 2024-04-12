package org.arteco.sersoc.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;


public abstract class AbstractCrudController<ENTITY, ID, REPO extends JpaRepository<ENTITY, ID>, SERVICE extends AbstractCrudService<ENTITY, ID, REPO>> {

    protected final SERVICE service;

    protected AbstractCrudController(SERVICE service) {
        this.service = service;
    }

    @PostMapping("/save")
    public String save(@RequestBody final ENTITY entity) {
        service.save(entity);
        return "redirect:/" + entity.getClass().getSimpleName().toLowerCase();
    }

    @PutMapping("/{id}")
    public String update(@PathVariable ID id, @RequestBody ENTITY entity) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        service.update(entity, id);
        return "redirect:/" + entity.getClass().getSimpleName().toLowerCase();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable ID id) {
        service.findById(id).ifPresent(service::delete);
        return "redirect:";
    }
}
