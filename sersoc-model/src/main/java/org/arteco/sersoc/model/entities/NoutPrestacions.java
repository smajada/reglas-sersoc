package org.arteco.sersoc.model.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.html.ListView;
import java.sql.Date;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "con")
@NoArgsConstructor
@Data
@Entity
@Table(name = "NOUT_PRESTACIONS")
public class NoutPrestacions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON")
    @JsonView(ListView.class)
    private Long con;

    //Año del expediente
    @Column(name = "EXP_ANY_ANO")
    @JsonView(ListView.class)
    private Integer expAnyAno;

    //Número del expediente dentro del año
    @Column(name = "EXP_CON")
    @JsonView(ListView.class)
    private Integer expCon;

    //Secuencia o número de apertura del expediente
    @Column(name = "EXP_SEQ")
    @JsonView(ListView.class)
    private Integer expSeq;

    //Fecha de inicio de la prestación
    @Column(name = "DATINI")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonView(ListView.class)
    private Date datIni;

    //Fecha de fin de la prestación
    @Column(name = "DATFIN")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonView(ListView.class)
    private Date datFin;

    //Importe
    @Column(name = "IMP")
    @JsonView(ListView.class)
    private Double imp;

    /*@ManyToOne
    @JoinColumn(name = "PER_CON")
    @JsonView(ListView.class)
    private NoutPersona noutPersona;*/

    @ManyToOne
    @JoinColumn(name = "TIPPRS_COA")
    @JsonView(ListView.class)
    private NoutTipprs tipoPrestacion;

    //Forma de pago
    @ManyToOne
    @JoinColumn(name = "FORPAG_COA")
    @JsonView(ListView.class)
    private NoutForpag forpag;

    @ManyToOne
    @JoinColumn(name = "TIPCOT_COA")
    @JsonView(ListView.class)
    private NoutTipcot tipcot;

    @ManyToOne
    @JoinColumn(name = "TIPRCP_COA")
    @JsonView(ListView.class)
    private NoutTiprcp tiprcp;

    @Column(name = "PER_CON")
    private Long perCon;
}