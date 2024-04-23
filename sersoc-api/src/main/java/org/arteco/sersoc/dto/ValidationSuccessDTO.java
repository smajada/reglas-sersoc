package org.arteco.sersoc.dto;

import lombok.Data;

@Data
public class ValidationSuccessDTO {

    private Long reglaId;

    private String description;

    private String message = "Regla validada.";
}
