package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglaTipoPrestacionRepository extends JpaRepository<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId> {
}
