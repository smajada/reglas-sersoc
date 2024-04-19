package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reglas_tipo_prestaciones")

public class ReglaTipoPrestacionEntity {

    @EmbeddedId
    private ReglasTipoPrestacionId id = new ReglasTipoPrestacionId();

    @ManyToOne
    @MapsId("reglaId")
    private NoutRegles noutRegles = new NoutRegles();

    @ManyToOne
    @MapsId("tipoPrestacionId")
    private NoutTipprs noutTipprs = new NoutTipprs();

    @Column(name = "active")
    private Boolean active = true;
}
