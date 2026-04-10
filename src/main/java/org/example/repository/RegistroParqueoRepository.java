package org.example.repository;

import org.example.model.RegistroParqueo;
import org.example.model.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroParqueoRepository extends JpaRepository<RegistroParqueo, Long> {
    Optional<RegistroParqueo> findByVehiculoPlacaAndEstado(String placa, EstadoRegistro estado);
    List<RegistroParqueo> findByVehiculoPlaca(String placa);
    List<RegistroParqueo> findByEstado(EstadoRegistro estado);
    boolean existsByVehiculoIdAndEstado(Long vehiculoId, EstadoRegistro estado);
}

