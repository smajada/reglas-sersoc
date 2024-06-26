package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.base.NoutPerPrsId;
import org.arteco.sersoc.model.entities.NoutPerPrs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoutPerPrsRepository  extends JpaRepository<NoutPerPrs, NoutPerPrsId> {

}