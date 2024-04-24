package org.arteco.sersoc.model.entities;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "con")
@NoArgsConstructor
@Data
@Entity
@Table(name = "NOUT_PRESTACIONS")
public class NoutPrestacions {

    //Número de prestación
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON")
    private Long con;

    //Año del expediente
    @Column(name = "EXP_ANY_ANO")
    private Integer expAnyAno;

    //Número del expediente dentro del año
    @Column(name = "EXP_CON")
    private Integer expCon;

    //Secuencia o número de apertura del expediente
    @Column(name = "EXP_SEQ")
    private Integer expSeq;

    //Fecha de inicio de la prestación
    @Column(name = "DATINI")
    private Date datIni;

    //Fecha de fin de la prestación
    @Column(name = "DATFIN")
    private Date datFin;

    //Importe
    @Column(name = "IMP")
    private Double imp;

    @ManyToOne
    @JoinColumn(name = "nout_tipprs_id")
    private NoutTipprs tipoPrestacion;

    //Forma de pago
    @ManyToOne
    @JoinColumn(name = "nout_forpag_coa")
    private NoutForpag forpag;

    @ManyToOne
    @JoinColumn(name = "nout_tipcot_coa")
    private NoutTipcot tipcot;

    @ManyToOne
    @JoinColumn(name = "nout_tiprcp_coa")
    private NoutTiprcp tiprcp;

    public NoutPrestacions(Integer expCon, Date datIni, Date datFin, Double imp, NoutTipprs tipoPrestacion) {
        this.expCon = expCon;
        this.datIni = datIni;
        this.datFin = datFin;
        this.imp = imp;
        this.tipoPrestacion = tipoPrestacion;
    }
}
