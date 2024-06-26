package org.arteco.sersoc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arteco.sersoc.model.entities.NoutForpag;
import org.arteco.sersoc.model.entities.NoutTipcot;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.model.entities.NoutTiprcp;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoutPrestacionsDTO {

    private Long con;
    private Integer expAnyAno;
    private Integer expCon;
    private Integer expSeq;
    private Date datIni;
    private Date datFin;
    private Double imp;
    private NoutTipprs tipoPrestacion;
    private NoutForpag forpag;
    private NoutTipcot tipcot;
    private NoutTiprcp tiprcp;
    private List<Map<String, Object>> beneficiaris;
}
