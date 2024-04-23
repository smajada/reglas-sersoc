package org.arteco.sersoc.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WarningDTO {

    private Long reglaId;

    private String description;

    private String message = "La regla está a punto de caducar.";
}
