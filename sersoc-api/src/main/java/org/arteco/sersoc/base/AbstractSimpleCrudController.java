package org.arteco.sersoc.base;

import org.arteco.sersoc.filter.BasicFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class AbstractSimpleCrudController<
        ENTITY,
        ID,
        FILTER extends BasicFilter,
        REPO extends JpaRepository<ENTITY, ID>,
        SERVICE extends AbstractCrudService<ENTITY, ID, REPO>>
        extends AbstractCrudController<ENTITY, ID, REPO, SERVICE> {


    public AbstractSimpleCrudController(SERVICE service) {
        super(service);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ENTITY> get(@PathVariable(value = "id") final ID id) {
        return super.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrDismiss(@PathVariable(value = "id") final ID id) {
        return super.deleteOrDismissById(id);
    }
}