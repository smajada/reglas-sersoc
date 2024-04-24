package org.arteco.sersoc.dto;

import lombok.Data;

@Data
public class ValidationWarningDTO {

    private Long reglaId;

    private String description;

    private String message = "La regla está a punto de caducar.";
}
