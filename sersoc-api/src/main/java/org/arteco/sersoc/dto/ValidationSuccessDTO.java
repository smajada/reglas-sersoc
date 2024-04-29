package org.arteco.sersoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationSuccessDTO {

    private Long reglaId;

    private String description;

    private String message = "Regla validada.";
}
