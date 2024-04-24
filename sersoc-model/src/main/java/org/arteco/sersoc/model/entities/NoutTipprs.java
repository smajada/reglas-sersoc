package org.arteco.sersoc.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@ToString
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "nout_tipprs_id")
    private List<NoutPrestacions> prestacions;

    public NoutTipprs(String dec, String dem, String tipprsCoa, String gruprsCoa, List<NoutPrestacions> prestacions) {
        this.dec = dec;
        this.dem = dem;
        this.tipprsCoa = tipprsCoa;
        this.gruprsCoa = gruprsCoa;
        this.prestacions = prestacions;
    }
}