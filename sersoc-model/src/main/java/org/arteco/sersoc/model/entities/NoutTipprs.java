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
    @ManyToOne
    @JoinColumn(name = "GRUPPRS_COA")
    private NoutGruprs gruprsCoa;


    public NoutTipprs(String dec, String dem, String tipprsCoa, NoutGruprs gruprsCoa) {
        this.dec = dec;
        this.dem = dem;
        this.tipprsCoa = tipprsCoa;
        this.gruprsCoa = gruprsCoa;
    }
}