package org.arteco.sersoc.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoutGruprs {

    @Id
    @Column(name = "COA")
    private String coa;

    @Column(name = "DEC", length = 15)
    private String dec;

    @Column(name = "DEM", length = 240)
    private String dem;

}
