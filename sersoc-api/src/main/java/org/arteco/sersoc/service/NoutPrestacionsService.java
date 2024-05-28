package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.repository.NoutPrestacionsRepository;
import org.springframework.stereotype.Service;

@Service
public class NoutPrestacionsService extends AbstractCrudService<NoutPrestacions, Long, NoutPrestacionsRepository> {


    public NoutPrestacionsService(NoutPrestacionsRepository noutPrestacionsRepository) {
        super(noutPrestacionsRepository);
    }

    @Override
    public void update(NoutPrestacions bean, Long aLong) {
        bean.setCon(aLong);
        repo.save(bean);
    }

    @Override
    public void delete(NoutPrestacions noutPrestacions) {
        repo.delete(noutPrestacions);
    }
}
