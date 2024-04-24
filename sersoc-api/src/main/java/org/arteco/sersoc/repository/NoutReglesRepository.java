package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.entities.NoutRegles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoutReglesRepository extends JpaRepository<NoutRegles, Long> {


    @Query("SELECT r " +
            "FROM NoutRegles r " +
            "JOIN ReglaTipoPrestacionEntity rtp " +
            "ON r.con = rtp.noutRegles.con " +
            "WHERE rtp.noutTipprs.coa = :idTipoPrestacion " +
            "AND r.active = true " +
            "AND rtp.active = true")
    List<NoutRegles> findByIdTipoPrestacion(String idTipoPrestacion);


}
