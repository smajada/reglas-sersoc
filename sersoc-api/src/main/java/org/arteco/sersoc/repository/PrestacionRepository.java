package org.arteco.sersoc.repository;


import org.arteco.sersoc.model.entities.PrestacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestacionRepository extends JpaRepository<PrestacionEntity, Long> {
}
