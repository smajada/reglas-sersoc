package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.entities.NoutSQLStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NoutSQLStatementRepository extends JpaRepository<NoutSQLStatement, Long> {

      Page<NoutSQLStatement> findByActiveTrue(Pageable pageable);
      List<NoutSQLStatement> findByActiveTrue();
   }
