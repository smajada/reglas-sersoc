package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.entities.NoutRegles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface NoutReglesRepository extends JpaRepository<NoutRegles, Long> {


	@Query("SELECT r " +
		"FROM NoutRegles r " +
		"JOIN ReglaTipoPrestacionEntity rtp " +
		"ON r.con = rtp.noutRegles.con " +
		"WHERE rtp.noutTipprs.coa = :idTipoPrestacion " +
		"AND r.active = true " +
        "AND r.datIni <= CURRENT_DATE " +
        "AND r.datFin >= CURRENT_DATE " +
		"AND rtp.active = true")
	List<NoutRegles> findByIdTipoPrestacion(String idTipoPrestacion);


	Page<NoutRegles> findByActiveTrue(Pageable pageable);


}
