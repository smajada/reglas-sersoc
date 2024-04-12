package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.entities.ReglaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglaRepository extends JpaRepository<ReglaEntity, Long> {
}
