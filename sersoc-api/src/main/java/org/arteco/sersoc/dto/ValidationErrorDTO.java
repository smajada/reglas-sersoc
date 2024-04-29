package org.arteco.sersoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDTO {

        private Long reglaId;

        private String description;

        private String message = "Regla no validada.";
}
