package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.entities.NoutTipprs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface NoutTipprsRepository extends JpaRepository<NoutTipprs, String> {
}
