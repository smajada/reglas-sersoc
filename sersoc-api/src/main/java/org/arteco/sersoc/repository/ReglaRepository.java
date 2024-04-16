package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.entities.ReglaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReglaRepository extends JpaRepository<ReglaEntity, Long> {


    @Query("SELECT r " +
            "FROM ReglaEntity r " +
            "JOIN ReglaTipoPrestacionEntity rtp " +
            "ON r.Id = rtp.reglaEntity.Id " +
            "WHERE rtp.tipoPrestacionEntity.Id = :idPrestacion ")
    List<ReglaEntity> findByIdPrestacion(Long idPrestacion);


}
