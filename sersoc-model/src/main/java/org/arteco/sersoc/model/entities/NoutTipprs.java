package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "NOUT_TIPPRS")
public class NoutTipprs {

    @Id
    @Column(name = "COA")
    private String coa;

    //Descripción corta
    @Column(name = "DEC")
    private String dec;

    //Descripción media
    @Column(name = "DEM")
    private String dem;

    //Código de tipo de prestación
    @Column(name = "TIPPRS_COA")
    private String tipprsCoa;

    //Código de grupo de prestaciones
    @Column(name = "GRUPPRS_COA")
    private String gruprsCoa;

    @OneToMany(mappedBy = "tipoPrestacion")
    private List<NoutPrestacions> prestacions;

    public NoutTipprs(String dec, String dem, String tipprsCoa, String gruprsCoa, List<NoutPrestacions> prestacions) {
        this.dec = dec;
        this.dem = dem;
        this.tipprsCoa = tipprsCoa;
        this.gruprsCoa = gruprsCoa;
        this.prestacions = prestacions;
    }
}