package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.entities.PrestacionEntity;
import org.arteco.sersoc.repository.PrestacionRepository;
import org.springframework.stereotype.Service;

@Service
public class PrestacionService extends AbstractCrudService<PrestacionEntity, Long, PrestacionRepository> {


    public PrestacionService(PrestacionRepository prestacionRepository ) {
        super(prestacionRepository);
    }

    @Override
    public void delete(PrestacionEntity prestacionEntity) {
        repo.delete(prestacionEntity);
    }


}
