package org.arteco.sersoc.repository;

import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReglaTipoPrestacionRepository extends JpaRepository<ReglaTipoPrestacionEntity, ReglasTipoPrestacionId> {

    /**
     * Método que busca una regla de tipo prestación por su nombre
     * @param keyword nombre de la regla de tipo prestación
     * @return lista de reglas de tipo prestación que coinciden con el nombre
     */
    @Query("SELECT r FROM ReglaTipoPrestacionEntity r WHERE r.reglaEntity.nombre LIKE %?1%"
            + " OR r.tipoPrestacionEntity.descripcion LIKE %?1%"
            + " OR r.reglaEntity.descripcion LIKE %?1%")
    List<ReglaTipoPrestacionEntity> search(String keyword);

}
