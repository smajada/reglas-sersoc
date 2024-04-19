package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.repository.NoutReglesRepository;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReglaTipoPrestacionService extends AbstractCrudService<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository> {

    private final NoutReglesService noutReglesService;

    public ReglaTipoPrestacionService(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository, NoutReglesRepository noutReglesRepository) {
        super(reglaTipoPrestacionRepository);
        this.noutReglesService = new NoutReglesService(noutReglesRepository);
    }

    @Transactional
    public void save(NoutRegles regla, List<NoutTipprs> tipoPrestacion) {
        NoutRegles savedRegla = noutReglesService.save(regla);

        for (NoutTipprs noutTipprs : tipoPrestacion) {
            ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
            reglaTipoPrestacion.setNoutRegles(savedRegla);
            reglaTipoPrestacion.setNoutTipprs(noutTipprs);

            if (!repo.existsById(new ReglasTipoPrestacionId(savedRegla.getCon(), noutTipprs.getCoa()))) {
                repo.save(reglaTipoPrestacion);
            }
        }
    }

    @Transactional
    public void updateAll(Long reglaId, NoutRegles regla, List<NoutTipprs> tiposPrestacion) {
        noutReglesService.update(regla, reglaId);

        List<ReglaTipoPrestacionEntity> reglasTipoPrestacion = repo.findByNoutRegles_Con(reglaId);

        for (ReglaTipoPrestacionEntity reglaTipoPrestacion : reglasTipoPrestacion) {
            if (tiposPrestacion.stream().noneMatch(tipo -> tipo.getCoa().equals(reglaTipoPrestacion.getNoutTipprs().getCoa()))) {
                delete(reglaTipoPrestacion);
            }
        }

        for (NoutTipprs noutTipprs : tiposPrestacion) {
            ReglaTipoPrestacionEntity existingEntity = repo
                    .findById(new ReglasTipoPrestacionId(reglaId, noutTipprs.getCoa()))
                    .orElse(null);
            if (existingEntity != null) {
                existingEntity.setActive(true);
                repo.save(existingEntity);
            } else {
                ReglaTipoPrestacionEntity newEntity = new ReglaTipoPrestacionEntity();
                newEntity.setNoutRegles(regla);
                newEntity.setNoutTipprs(noutTipprs);
                repo.save(newEntity);
            }
        }
    }

    @Transactional
    @Override
    public void delete(ReglaTipoPrestacionEntity reglaTipoPrestacion) {
        reglaTipoPrestacion.setActive(false);
        repo.save(reglaTipoPrestacion);
    }

    @Transactional
    @Override
    public void update(ReglaTipoPrestacionEntity bean, ReglasTipoPrestacionId reglasTipoPrestacionId) {
        ReglaTipoPrestacionEntity reglaTipoPrestacion = repo.findById(reglasTipoPrestacionId).orElseThrow();
        reglaTipoPrestacion.setNoutRegles(bean.getNoutRegles());
        reglaTipoPrestacion.setNoutTipprs(bean.getNoutTipprs());
        repo.save(reglaTipoPrestacion);
    }
}