package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOUT_TRAMITACIO")
public class NoutTramitacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON")
    private Integer con;

    //Data tràmit
    @Column(name = "DAT")
    private Date dat;

    //Import final (total consumit)
    @Column(name = "IMPFIN")
    private Integer impFin;

    //Data baixa prestació
    @Column(name = "DAT_BAI")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datBai;

    //Número de tràmit
    @ManyToOne
    @JoinColumn(name = "TRA_CON")
    private NoutTramits noutTramits;

    //Número de prestació
    @ManyToOne
    @JoinColumn(name = "PRS_CON")
    private NoutPrestacions noutPrestacions;
}
