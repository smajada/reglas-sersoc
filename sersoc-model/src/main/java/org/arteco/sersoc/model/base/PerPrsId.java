package org.arteco.sersoc.model.base;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerPrsId implements Serializable {

    private Long perId;
    private Long prsId;
}
