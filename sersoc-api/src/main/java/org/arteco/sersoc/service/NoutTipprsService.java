package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.repository.NoutTipprsRepository;
import org.springframework.stereotype.Service;


@Service
public class NoutTipprsService extends AbstractCrudService<NoutTipprs, String, NoutTipprsRepository> {

    public NoutTipprsService(NoutTipprsRepository noutTipprsRepository) {
        super(noutTipprsRepository);
    }

    @Override
    public void update(NoutTipprs bean, String s) {
        bean.setCoa(s);
        repo.save(bean);
    }

    @Override
    public void delete(NoutTipprs bean) {
        repo.delete(bean);
    }
}
