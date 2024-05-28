package org.arteco.sersoc.model.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoutForpag {

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
}
