package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReglaTipoPrestacionService extends AbstractCrudService<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository> {

    public ReglaTipoPrestacionService(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository) {
        super(reglaTipoPrestacionRepository);
    }

    @Override
    public void delete(ReglaTipoPrestacionEntity bean) {
        repo.delete(bean);
    }




}

