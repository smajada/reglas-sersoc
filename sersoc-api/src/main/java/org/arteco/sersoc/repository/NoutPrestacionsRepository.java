package org.arteco.sersoc.repository;


import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoutPrestacionsRepository extends JpaRepository<NoutPrestacions, Long> {
}
