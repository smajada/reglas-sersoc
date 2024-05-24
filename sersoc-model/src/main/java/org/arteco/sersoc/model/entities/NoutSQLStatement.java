package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOUT_SQL")
public class NoutSQLStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON")
    private Long con;

    @Column(name = "KEY", length = 250, unique = true)
    @NotEmpty(message = "Key is required")
    private String key;

    @Column(name = "DESC_SQL", length = 500)
    @NotEmpty(message = "Description is required")
    private String description;

    @Column(name = "VALUE", length = 65535)
    private String value;

    @Column(name = "ACTIVE")
    private Boolean active = true;
}
