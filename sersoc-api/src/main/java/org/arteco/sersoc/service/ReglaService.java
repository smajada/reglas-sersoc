package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.entities.ReglaEntity;
import org.arteco.sersoc.repository.ReglaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReglaService extends AbstractCrudService<ReglaEntity, Long, ReglaRepository> {


    public ReglaService(ReglaRepository reglaRepository, TipoPrestacionService tipoPrestacionService) {
        super(reglaRepository);
    }

    @Override
    public void delete(ReglaEntity bean) {
        bean.setActive(false);
    }

    public List<ReglaEntity> findByIdPrestacion(Long idPrestacion){
        return repo.findByIdPrestacion(idPrestacion);
    }
}
