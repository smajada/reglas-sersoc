package org.arteco.sersoc.service;

import org.arteco.sersoc.base.AbstractCrudService;
import org.arteco.sersoc.dto.PageDto;
import org.arteco.sersoc.model.entities.NoutSQLStatement;
import org.arteco.sersoc.repository.NoutSQLStatementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoutSQLStatementService extends AbstractCrudService<NoutSQLStatement, Long, NoutSQLStatementRepository> {
    public NoutSQLStatementService(NoutSQLStatementRepository noutSQLStatementRepository) {
        super(noutSQLStatementRepository);
    }

    @Override
    public void update(NoutSQLStatement bean, Long aLong) {
        bean.setCon(aLong);
        repo.save(bean);
    }

    @Override
    public void delete(NoutSQLStatement bean) {
        bean.setActive(false);
        repo.save(bean);
    }

    public PageDto<NoutSQLStatement> findByActiveTrue(Pageable pageable) {
        Page<NoutSQLStatement> statements = repo.findByActiveTrue(pageable);
        return new PageDto<>(statements);
    }

    public Map<String, String> getAllSqlStatement() {
        return repo.findAll()
                .stream()
                .collect(Collectors
                        .toMap(NoutSQLStatement::getKey,
                                NoutSQLStatement::getValue));
    }

    public Map<String, String> getAllSqlStatementByActive() {
        return repo.findByActiveTrue()
                .stream()
                .collect(Collectors
                        .toMap(NoutSQLStatement::getKey,
                                NoutSQLStatement::getValue));
    }
}
