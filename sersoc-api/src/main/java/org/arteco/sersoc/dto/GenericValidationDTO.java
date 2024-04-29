package org.arteco.sersoc.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenericValidationDTO {

    private List<ValidationSuccessDTO> success = new ArrayList<>();

    private List<ValidationErrorDTO> error = new ArrayList<>();

    private List<ValidationWarningDTO> warning = new ArrayList<>();

    public void addSuccess(Long reglaId, String description, String message) {
        success.add(new ValidationSuccessDTO(reglaId, description, message));
    }

    public void addError(Long reglaId, String description, String message) {
        error.add(new ValidationErrorDTO(reglaId, description, message));
    }

    public void addWarning(Long reglaId, String description, String message) {
        warning.add(new ValidationWarningDTO(reglaId, description, message));
    }

}
