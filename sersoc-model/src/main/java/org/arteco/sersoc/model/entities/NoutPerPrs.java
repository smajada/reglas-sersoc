package org.arteco.sersoc.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @MapsId("perId")
    @JoinColumn(name = "PER_CON")
    private NoutPersona noutPersona;

    @ManyToOne
    @JsonBackReference
    @MapsId("prsId")
    @JoinColumn(name = "PRS_CON")
    private NoutPrestacions noutPrestacions;

}
