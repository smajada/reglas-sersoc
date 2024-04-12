package org.arteco.sersoc.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "prestaciones")
public class PrestacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nombre_prestacion;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "tipo_prestacion_id")
    private TipoPrestacionEntity tipoPrestacion;

    public PrestacionEntity(String nombre_prestacion, String descripcion) {
        this.nombre_prestacion = nombre_prestacion;
        this.descripcion = descripcion;
    }
}
