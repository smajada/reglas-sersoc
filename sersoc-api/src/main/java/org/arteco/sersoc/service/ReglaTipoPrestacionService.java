package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaEntity;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.repository.ReglaRepository;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.repository.NoutTipprsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReglaTipoPrestacionService extends AbstractCrudService<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository> {

    private final ReglaRepository reglaRepository;
    private final NoutTipprsRepository noutTipprsRepository;


    public ReglaTipoPrestacionService(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository, ReglaRepository reglaRepository, NoutTipprsRepository noutTipprsRepository) {
        super(reglaTipoPrestacionRepository);
        this.reglaRepository = reglaRepository;
        this.noutTipprsRepository = noutTipprsRepository;
    }

    public void saveReglaWithTipoPrestacion(ReglaEntity regla, List<NoutTipprs> tipoPrestacion){
        ReglaEntity savedRegla = reglaRepository.save(regla);

        for (NoutTipprs noutTipprs : tipoPrestacion) {
            ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
            reglaTipoPrestacion.setReglaEntity(savedRegla);
            reglaTipoPrestacion.setNoutTipprs(noutTipprs);
            this.repo.save(reglaTipoPrestacion);
        }
    }

    @Override
    public void delete(ReglaTipoPrestacionEntity bean) {
        bean.getReglaEntity().setActive(false);
        repo.save(bean);
    }

}

