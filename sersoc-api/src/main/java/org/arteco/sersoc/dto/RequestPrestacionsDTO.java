package org.arteco.sersoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.NoutTipprs;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class RequestPrestacionsDTO {

    private final NoutRegles noutRegles;

    private final List<NoutTipprs> tiposPrestacion;

}
