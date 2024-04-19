package org.arteco.sersoc.base;

import org.arteco.sersoc.model.entities.NoutRegles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class AbstractCrudService<
        ENTITY,
        ID,
        REPO extends JpaRepository<ENTITY, ID>> extends AbstractSecureController {

    protected final REPO repo;

    public AbstractCrudService(REPO repo) {
        this.repo = repo;
    }

    public Iterable<ENTITY> findAll() {
        return this.repo.findAll();
    }

    public Optional<ENTITY> findById(ID id) {
        return this.repo.findById(id);
    }

    public ENTITY save(ENTITY bean) {
        return this.repo.save(bean);
    }

    public abstract void update(ENTITY bean, ID id);

    public List<ENTITY> findAllById(List<ID> ids) {
        return this.repo.findAllById(ids);
    }

    public Page<ENTITY> page(final Pageable page) {
       return this.repo.findAll(page);
    }

    public abstract void delete(ENTITY bean);
}
