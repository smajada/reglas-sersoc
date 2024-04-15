package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@NoArgsConstructor
@Data
@Entity
@Table(name = "reglas")
public class ReglaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nombre;

    private String descripcion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_inicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_fin;

    @Column(columnDefinition = "TEXT")
    private String script;

    private boolean active = true;
}
