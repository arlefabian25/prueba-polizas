package com.prueba.polizas.entity;

import com.prueba.polizas.entity.enums.EstadoRiesgo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** Inmueble arrendado asegurado dentro de una póliza. */
@Entity
@Table(name = "riesgos")
@Getter
@Setter
@NoArgsConstructor
public class Riesgo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poliza_id", nullable = false)
    private Poliza poliza;

    @Column(nullable = false, length = 200)
    private String direccionInmueble;

    /** Asegurado. */
    @Column(nullable = false, length = 150)
    private String arrendatario;

    /** Beneficiario. */
    @Column(nullable = false, length = 150)
    private String arrendador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoRiesgo estado;

    @Column(nullable = false)
    private LocalDate fechaInclusion;

    private LocalDate fechaCancelacion;
}
