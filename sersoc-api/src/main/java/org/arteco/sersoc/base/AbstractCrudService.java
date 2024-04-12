package org.arteco.sersoc.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


public abstract class AbstractCrudService<ENTITY, ID, REPO extends JpaRepository<ENTITY, ID>> {

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

    public void save(ENTITY bean) {
        this.repo.save(bean);
    }

    public void update(ENTITY bean, ID id) {
        if (this.repo.existsById(id)) {
            this.repo.save(bean);
        } else {
            throw new IllegalArgumentException("No existe el registro con id: " + id);
        }
    }

    public abstract void delete(ENTITY bean);

}
