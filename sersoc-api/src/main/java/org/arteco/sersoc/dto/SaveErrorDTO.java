package org.arteco.sersoc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Getter
@AllArgsConstructor
public class SaveErrorDTO {
    String field;
    String message;
    String code;
    boolean show;

    public SaveErrorDTO(ObjectError error) {
        this.code = error.getCode();
        this.message = error.getDefaultMessage();
        if (error instanceof FieldError fe) {
            this.field = fe.getField();
        }
    }
}
