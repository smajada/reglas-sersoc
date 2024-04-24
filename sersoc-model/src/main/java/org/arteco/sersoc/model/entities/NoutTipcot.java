package org.arteco.sersoc.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOUT_TIPCOT")
public class NoutTipcot {

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

    @OneToMany(mappedBy = "tipcot")
    @JsonManagedReference
    private List<NoutPrestacions> prestacions;
}
