package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.entities.ReglaEntity;
import org.arteco.sersoc.model.entities.TipoPrestacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TipoPrestacionRepository extends JpaRepository<TipoPrestacionEntity, Long> {
}
