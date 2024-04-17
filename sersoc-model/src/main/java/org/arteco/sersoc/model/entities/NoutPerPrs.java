package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arteco.sersoc.model.base.PerPrsId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOUT_PER_PRS")
public class NoutPerPrs {

    @EmbeddedId
    private PerPrsId id = new PerPrsId();

    @ManyToOne
    @MapsId("perId")
    private NoutPersona noutPersona;

    @ManyToOne
    @MapsId("prsId")
    private NoutPrestacions noutPrestacions;

}
