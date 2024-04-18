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
    public void delete(NoutRegles bean) {
        bean.setActive(false);
        repo.save(bean);
    }

    public List<NoutRegles> findByIdPrestacion(Long idPrestacion){
        return repo.findByIdPrestacion(idPrestacion);
    }
}
