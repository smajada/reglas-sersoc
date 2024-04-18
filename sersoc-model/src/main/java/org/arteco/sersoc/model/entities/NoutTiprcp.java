package org.arteco.sersoc.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOUT_TIPRCP")
public class NoutTiprcp {

    //Tipo de prestación
    @Id
    @Column(name = "COA", length = 2)
    private String coa;

    //Descripción corta
    @Column(name = "DEC", length = 15)
    private String dec;

    //Descripción media
    @Column(name = "DEM", length = 240)
    private String dem;

    @OneToMany(mappedBy = "tiprcp")
    private List<NoutPrestacions> prestacions;
}
