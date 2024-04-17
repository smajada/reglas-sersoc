package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.repository.NoutReglesRepository;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.arteco.sersoc.repository.NoutTipprsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReglaTipoPrestacionService extends AbstractCrudService<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository> {

    private final NoutReglesRepository noutReglesRepository;
    private final NoutTipprsRepository noutTipprsRepository;


    public ReglaTipoPrestacionService(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository, NoutReglesRepository noutReglesRepository, NoutTipprsRepository noutTipprsRepository) {
        super(reglaTipoPrestacionRepository);
        this.noutReglesRepository = noutReglesRepository;
        this.noutTipprsRepository = noutTipprsRepository;
    }

    public void saveSingleReglaWithTipoPrestacion(ReglaTipoPrestacionEntity newReglaTipoPrestacion){
        NoutRegles savedRegla = noutReglesRepository.save(newReglaTipoPrestacion.getNoutRegles());
        NoutTipprs savedTipoPrestacion = noutTipprsRepository.findById(newReglaTipoPrestacion.getNoutTipprs().getCoa()).orElseThrow();

        ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
        reglaTipoPrestacion.setNoutRegles(savedRegla);
        reglaTipoPrestacion.setNoutTipprs(savedTipoPrestacion);

        this.repo.save(reglaTipoPrestacion);
    }

    public void saveReglaWithTipoPrestacion(NoutRegles regla, List<NoutTipprs> tipoPrestacion){
        NoutRegles savedRegla = noutReglesRepository.save(regla);

        for (NoutTipprs noutTipprs : tipoPrestacion) {
            ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
            reglaTipoPrestacion.setNoutRegles(savedRegla);
            reglaTipoPrestacion.setNoutTipprs(noutTipprs);

            // Check if the ReglaTipoPrestacion already exists
            if (repo.findById(new ReglasTipoPrestacionId(savedRegla.getCon(), noutTipprs.getCoa())).isPresent()){
                continue;
            }

            this.repo.save(reglaTipoPrestacion);
        }
    }

    @Override
    public void delete(ReglaTipoPrestacionEntity bean) {
        bean.getNoutRegles().setActive(false);
        repo.save(bean);
    }

}

