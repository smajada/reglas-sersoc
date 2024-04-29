package org.arteco.sersoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationWarningDTO {

    private Long reglaId;

    private String description;

    private String message = "La regla está a punto de caducar.";
}
