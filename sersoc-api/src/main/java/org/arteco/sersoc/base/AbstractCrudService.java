package org.arteco.sersoc.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public abstract class AbstractCrudService<
        ENTITY,
        ID,
        REPO extends JpaRepository<ENTITY, ID>> extends AbstractSecureController {

    protected final REPO repo;

    protected AbstractCrudService(REPO repo) {
        this.repo = repo;
    }

    public Optional<ENTITY> findById(final ID id) {
        return this.repo.findById(id);
    }

    public ENTITY save(final ENTITY bean) {
        return this.repo.save(bean);
    }

    public void deleteOrDismiss(final ENTITY bean) {
        this.repo.delete(bean);
    }

    public Page<ENTITY> page(final Pageable page) {
        return this.repo.findAll(page);
    }
    public Stream<ENTITY> list(final Sort sort) {
        final Iterable<ENTITY> data = this.repo.findAll(sort);
        return StreamSupport.stream(data.spliterator(), false);
    }

    public Iterable<ENTITY> findAll() {
        return this.repo.findAll();
    }

    public List<ENTITY> findAllById(List<ID> ids) {
        return this.repo.findAllById(ids);
    }


    public abstract void update(ENTITY bean, ID id);

    public abstract void delete(ENTITY bean);
}
