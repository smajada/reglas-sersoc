package org.arteco.sersoc.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenericValidationDTO {

    private List<ValidationSuccessDTO> success = new ArrayList<>();

    private List<ValidationErrorDTO> error = new ArrayList<>();

    private List<WarningDTO> warning = new ArrayList<>();

}
