package org.arteco.sersoc.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "tipo_prestaciones")
public class TipoPrestacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String descripcion;

    @OneToMany(mappedBy = "tipoPrestacion", cascade = CascadeType.ALL)
    private List<PrestacionEntity> prestaciones;

    public TipoPrestacionEntity(String descripcion) {
        this.descripcion = descripcion;
    }
}