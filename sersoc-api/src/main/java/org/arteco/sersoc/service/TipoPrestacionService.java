package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.entities.TipoPrestacionEntity;
import org.arteco.sersoc.repository.TipoPrestacionRepository;
import org.springframework.stereotype.Service;


@Service
public class TipoPrestacionService extends AbstractCrudService<TipoPrestacionEntity, Long, TipoPrestacionRepository> {

    public TipoPrestacionService(TipoPrestacionRepository tipoPrestacionRepository) {
        super(tipoPrestacionRepository);
    }

    @Override
    public void delete(TipoPrestacionEntity bean) {
        repo.delete(bean);
    }
}
