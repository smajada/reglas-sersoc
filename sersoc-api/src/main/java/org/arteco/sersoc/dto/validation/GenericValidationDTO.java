package org.arteco.sersoc.dto.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class GenericValidationDTO {

    private String message;
    private String type;

    public void addSuccess(String message) {
        this.setMessage(message);
        this.setType("SUCCESS");
    }

    public void addError(String message) {
        this.setMessage(message);
        this.setType("ERROR");
    }

    public void addWarning(String message) {
        this.setMessage(message);
        this.setType("WARNING");
    }

}
