package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaEntity;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.model.entities.TipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaRepository;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.repository.TipoPrestacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReglaTipoPrestacionService extends AbstractCrudService<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository> {

    private final ReglaRepository reglaRepository;
    private final TipoPrestacionRepository tipoPrestacionRepository;


    public ReglaTipoPrestacionService(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository, ReglaRepository reglaRepository, TipoPrestacionRepository tipoPrestacionRepository) {
        super(reglaTipoPrestacionRepository);
        this.reglaRepository = reglaRepository;
        this.tipoPrestacionRepository = tipoPrestacionRepository;
    }

    public void saveReglaWithTipoPrestacion(ReglaEntity regla, List<TipoPrestacionEntity> tipoPrestacion){
        ReglaEntity savedRegla = reglaRepository.save(regla);

        for (TipoPrestacionEntity tipoPrestacionEntity : tipoPrestacion) {
            ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
            reglaTipoPrestacion.setReglaEntity(savedRegla);
            reglaTipoPrestacion.setTipoPrestacionEntity(tipoPrestacionEntity);
            this.repo.save(reglaTipoPrestacion);
        }
    }

    @Override
    public void delete(ReglaTipoPrestacionEntity bean) {
        bean.getReglaEntity().setActive(false);
        repo.save(bean);
    }
}

