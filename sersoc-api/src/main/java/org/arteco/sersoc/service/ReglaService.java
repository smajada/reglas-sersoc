package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.entities.ReglaEntity;
import org.arteco.sersoc.repository.ReglaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReglaService extends AbstractCrudService<ReglaEntity, Long, ReglaRepository> {

    private final TipoPrestacionService tipoPrestacionService;

    public ReglaService(ReglaRepository reglaRepository, TipoPrestacionService tipoPrestacionService) {
        super(reglaRepository);
        this.tipoPrestacionService = tipoPrestacionService;
    }

    @Override
    public void delete(ReglaEntity bean) {
        bean.setActive(false);
    }


}
