package com.prueba.polizas.entity;

import com.prueba.polizas.entity.enums.EstadoPoliza;
import com.prueba.polizas.entity.enums.TipoPoliza;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "polizas")
@Getter
@Setter
@NoArgsConstructor
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroPoliza;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPoliza tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPoliza estado;

    /** Arrendatario en una individual; inmobiliaria o administración en una colectiva. */
    @Column(nullable = false, length = 150)
    private String tomador;

    /** Canon mensual de arrendamiento. */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal canon;

    /** Prima = canon mensual x meses de vigencia. */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal prima;

    @Column(nullable = false)
    private Integer mesesVigencia;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    private LocalDate fechaCancelacion;
}
