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
    private final NoutReglesService noutReglesService;


    public ReglaTipoPrestacionService(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository, NoutReglesRepository noutReglesRepository, NoutTipprsRepository noutTipprsRepository) {
        super(reglaTipoPrestacionRepository);
        this.noutReglesRepository = noutReglesRepository;
        this.noutTipprsRepository = noutTipprsRepository;
        this.noutReglesService = new NoutReglesService(noutReglesRepository);
    }

    public void saveReglaWithTipoPrestacion(NoutRegles regla, List<NoutTipprs> tipoPrestacion) {
        NoutRegles savedRegla = noutReglesRepository.save(regla);

        for (NoutTipprs noutTipprs : tipoPrestacion) {
            ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
            reglaTipoPrestacion.setNoutRegles(savedRegla);
            reglaTipoPrestacion.setNoutTipprs(noutTipprs);

            // Check if the ReglaTipoPrestacion already exists
            if (repo.findById(new ReglasTipoPrestacionId(savedRegla.getCon(), noutTipprs.getCoa())).isPresent()) {
                continue;
            }

            this.repo.save(reglaTipoPrestacion);
        }
    }

    public void updateReglaWithTipoPrestacion(Long reglaId, NoutRegles regla, List<NoutTipprs> tiposPrestacion) {
        // Actualiza la regla
        noutReglesService.update(regla, reglaId);

        // Obtiene todas las reglasTipoPrestacion
        List<ReglaTipoPrestacionEntity> reglasTipoPrestacion = repo.findAll();

        // Recorre todas las reglasTipoPrestacion
        for (ReglaTipoPrestacionEntity reglaTipoPrestacion : reglasTipoPrestacion) {

            // Comprueba si la reglaTipoPrestacion coincide con la regla
            if (reglaTipoPrestacion.getNoutRegles().getCon().equals(regla.getCon())) {

                // Recorre todos los tiposPrestacion
                for (NoutTipprs noutTipprs : tiposPrestacion) {
                    ReglasTipoPrestacionId reglasTipoPrestacionId = new ReglasTipoPrestacionId(regla.getCon(), noutTipprs.getCoa());


                    // Comprueba si ya existe la reglaTipoPrestacion
                    if (repo.findById(reglasTipoPrestacionId).isPresent()) {

                        // Si ya existe, y su valor es false, lo activa
                        if(reglaTipoPrestacion.getActive().equals(false)){
                            reglaTipoPrestacion.setActive(true);
                            repo.save(reglaTipoPrestacion);
                            continue;
                        }

                        if (!tiposPrestacion.contains(reglaTipoPrestacion.getNoutTipprs())) {
                            this.delete(reglaTipoPrestacion);
                        }

                    } else {
                        ReglaTipoPrestacionEntity reglaTipoPrestacionEntity = new ReglaTipoPrestacionEntity();
                        reglaTipoPrestacionEntity.setNoutRegles(regla);
                        reglaTipoPrestacionEntity.setNoutTipprs(noutTipprs);
                        repo.save(reglaTipoPrestacionEntity);
                    }

                }
            }
        }
    }

    @Override
    public void delete(ReglaTipoPrestacionEntity reglaTipoPrestacion) {
        reglaTipoPrestacion.setActive(false);
        repo.save(reglaTipoPrestacion);
    }

    @Override
    public void update(ReglaTipoPrestacionEntity bean, ReglasTipoPrestacionId reglasTipoPrestacionId) {
        ReglaTipoPrestacionEntity reglaTipoPrestacion = repo.findById(reglasTipoPrestacionId).orElseThrow();
        reglaTipoPrestacion.setNoutRegles(bean.getNoutRegles());
        reglaTipoPrestacion.setNoutTipprs(bean.getNoutTipprs());
        repo.save(reglaTipoPrestacion);
    }
}

