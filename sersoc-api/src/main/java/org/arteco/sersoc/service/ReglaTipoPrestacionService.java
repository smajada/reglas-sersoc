package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReglaTipoPrestacionService extends AbstractCrudService<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId, ReglaTipoPrestacionRepository> {


    public ReglaTipoPrestacionService(ReglaTipoPrestacionRepository reglaTipoPrestacionRepository) {
        super(reglaTipoPrestacionRepository);
    }

//    public Page<ReglaTipoPrestacionEntity> findPaginated(Pageable pageable, String keyword){
//        int pageSize = pageable.getPageSize();
//        int currentPage = pageable.getPageNumber();
//        int startItem = currentPage * pageSize;
//
//        List<ReglaTipoPrestacionEntity> list = repo.findAll();
//
//        if(keyword != null){
//            list = repo.search(keyword);
//        }
//
//        if (list.size() < startItem) {
//            list = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, list.size());
//            list = list.subList(startItem, toIndex);
//        }
//
//        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), list.size());
//    }



    @Override
    public void delete(ReglaTipoPrestacionEntity bean) {
        bean.getReglaEntity().setActive(false);
        repo.save(bean);
    }



}

