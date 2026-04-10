package org.example.repository;

import org.example.model.Tarifa;
import org.example.model.enums.TipoTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    Optional<Tarifa> findByTipoTarifaAndTipoVehiculoIdAndActivaTrue(TipoTarifa tipoTarifa, Long tipoVehiculoId);
    List<Tarifa> findByActivaTrue();
    List<Tarifa> findByTipoTarifa(TipoTarifa tipoTarifa);
    List<Tarifa> findByTipoVehiculoId(Long tipoVehiculoId);
}

