package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "NOUT_TRAMITS")
public class NoutTramits {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON")
    private Integer con;

    //Tràmit
    @Column(name = "DEC")
    private String dec;

    //Tràmit amp.
    @Column(name = "DEM")
    private String dem;

//    @OneToMany(mappedBy = "noutTramits")
//    private List<NoutTramitacio> noutTramitacions;

}
