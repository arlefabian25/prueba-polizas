package com.prueba.polizas.repository;

import com.prueba.polizas.entity.Poliza;
import com.prueba.polizas.entity.enums.EstadoPoliza;
import com.prueba.polizas.entity.enums.TipoPoliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PolizaRepository extends JpaRepository<Poliza, Long> {

    /** Filtros opcionales: si un parámetro llega nulo, no se filtra por él. */
    @Query("""
            SELECT p FROM Poliza p
            WHERE (:tipo IS NULL OR p.tipo = :tipo)
              AND (:estado IS NULL OR p.estado = :estado)
            ORDER BY p.id
            """)
    List<Poliza> buscar(@Param("tipo") TipoPoliza tipo, @Param("estado") EstadoPoliza estado);
}
