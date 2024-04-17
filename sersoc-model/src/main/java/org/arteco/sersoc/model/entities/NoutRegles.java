package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;


@NoArgsConstructor
@Data
@Entity
@Table(name = "NOUT_REGLES")
public class NoutRegles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON")
    private Long con;

    @Column(name = "DEC")
    private String dec;

    //Fecha de inicio de la regla
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DATINI")
    private Date datIni;

    //Fecha de fin de la regla
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DATFIN")
    private Date datFin;

    @Column(columnDefinition = "TEXT", name = "SCR")
    private String script;

    private boolean active = true;
}
