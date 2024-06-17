package org.arteco.sersoc.base;

import lombok.Getter;
import org.arteco.sersoc.dto.SaveErrorDTO;

import java.util.List;

@Getter
public class BindingResultException extends Throwable {
    private final List<SaveErrorDTO> errors;

    public BindingResultException(List<SaveErrorDTO> errors) {
        this.errors= errors;
    }
}

