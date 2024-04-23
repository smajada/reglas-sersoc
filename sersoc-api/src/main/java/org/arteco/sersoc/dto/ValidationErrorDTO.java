package org.arteco.sersoc.dto;

import lombok.Data;

@Data
public class ValidationErrorDTO {

        private Long reglaId;

        private String description;

        private String message = "Regla no validada.";
}
