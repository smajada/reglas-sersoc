package org.arteco.sersoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReglaDTO {

    @NotEmpty(message = "El identificador no puede ser vacío o nulo")
    private String dec;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datIni;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datFin;

    @NotEmpty(message = "El script no puede ser nulo o vacío")
    private String script;

    private List<NoutTipprs> allTipoPrestacion;

    private List<String> reglasTipoPrestacionList;

}
