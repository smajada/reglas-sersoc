package org.arteco.sersoc.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NoutPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON")
    private Long con;

    @Column(name = "DNI", length = 9)
    private String dni;

    @Column(name="SEXE", length = 1)
    private String sexe;

    @Column(name = "DATA_NAIXEMENT")
    private Date dataNaixement;

    @Column(name = "EDAT")
    private Integer edat;
}
