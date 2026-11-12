package com.prueba.polizas.repository;

import com.prueba.polizas.entity.Riesgo;
import com.prueba.polizas.entity.enums.EstadoRiesgo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiesgoRepository extends JpaRepository<Riesgo, Long> {

    List<Riesgo> findByPolizaIdOrderById(Long polizaId);

    List<Riesgo> findByPolizaIdAndEstado(Long polizaId, EstadoRiesgo estado);
}
