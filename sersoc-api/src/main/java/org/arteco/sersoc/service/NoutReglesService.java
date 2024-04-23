package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.repository.NoutReglesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoutReglesService extends AbstractCrudService<NoutRegles, Long, NoutReglesRepository> {


    public NoutReglesService(NoutReglesRepository noutReglesRepository) {
        super(noutReglesRepository);
    }

    @Override
    public void update(NoutRegles bean, Long aLong) {
        bean.setCon(aLong);
        repo.save(bean);
    }

    @Override
    public void delete(NoutRegles bean) {
        bean.setActive(false);
        repo.save(bean);
    }

    public List<NoutRegles> findByIdPrestacion(String idTipPrestacion){
        return repo.findByIdPrestacion(idTipPrestacion);
    }
}
