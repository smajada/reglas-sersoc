package org.arteco.sersoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.NoutTipprs;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReglaDTO {

    private NoutRegles regla;
    private List<NoutTipprs> allTipoPrestacion;
    private List<String> reglasTipoPrestacionList;

}
