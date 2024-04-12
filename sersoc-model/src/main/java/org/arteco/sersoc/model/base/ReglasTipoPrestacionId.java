package org.arteco.sersoc.model.base;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ReglasTipoPrestacionId implements Serializable {

    private Long reglaId;
    private Long tipoPrestacionId;
}
