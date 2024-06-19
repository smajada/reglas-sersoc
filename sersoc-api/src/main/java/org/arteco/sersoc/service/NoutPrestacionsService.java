package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.dto.NoutPrestacionsDTO;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.repository.NoutPerPrsRepository;
import org.arteco.sersoc.repository.NoutPrestacionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NoutPrestacionsService extends AbstractCrudService<NoutPrestacions, Long, NoutPrestacionsRepository> {

    private final DataService dataService;


    protected NoutPrestacionsService(NoutPrestacionsRepository noutPrestacionsRepository, NoutPerPrsRepository noutPerPrsRepository, DataService dataService) {
        super(noutPrestacionsRepository);
        this.dataService = dataService;
    }

    public Optional<NoutPrestacionsDTO> findByIdPerPrs(final Long idPrestacio) {
        String sql = "SELECT per_prs.* " +
            "FROM NOUVIS_PER_PRS_AMP  per_prs, gen_persones  gen " +
            "WHERE per_prs.prs_con = ? AND per_prs.per_con = gen.con";
        Optional<NoutPrestacions> optionalEntity = super.findById(idPrestacio);

        if (optionalEntity.isPresent()) {
            List<Map<String, Object>> noutPerPrsList = dataService.selectMany(sql, optionalEntity.get().getCon());
            return Optional.of(convertToDto(optionalEntity.get(), noutPerPrsList));
        }
        return Optional.empty();
    }

    private NoutPrestacionsDTO convertToDto(NoutPrestacions noutPrestacions, List<Map<String, Object>> perPrs) {
        NoutPrestacionsDTO dto = new NoutPrestacionsDTO();
        dto.setCon(noutPrestacions.getCon());
        dto.setExpAnyAno(noutPrestacions.getExpAnyAno());
        dto.setExpCon(noutPrestacions.getExpCon());
        dto.setExpSeq(noutPrestacions.getExpSeq());
        dto.setDatIni(noutPrestacions.getDatIni());
        dto.setDatFin(noutPrestacions.getDatFin());
        dto.setImp(noutPrestacions.getImp());
        dto.setTipoPrestacion(noutPrestacions.getTipoPrestacion());
        dto.setForpag(noutPrestacions.getForpag());
        dto.setTipcot(noutPrestacions.getTipcot());
        dto.setTiprcp(noutPrestacions.getTiprcp());
        dto.setBeneficiaris(perPrs);
        return dto;
    }

    @Override
    public void update(NoutPrestacions bean, Long aLong) {
        bean.setCon(aLong);
        repo.save(bean);
    }

    @Override
    public void delete(NoutPrestacions noutPrestacions) {
        repo.delete(noutPrestacions);
    }
}
