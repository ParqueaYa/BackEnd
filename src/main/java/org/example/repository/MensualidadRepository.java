package org.example.repository;

import org.example.model.Mensualidad;
import org.example.model.enums.EstadoMensualidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MensualidadRepository extends JpaRepository<Mensualidad, Long> {
    List<Mensualidad> findByClienteId(Long clienteId);
    List<Mensualidad> findByVehiculoId(Long vehiculoId);
    List<Mensualidad> findByEstado(EstadoMensualidad estado);
    Optional<Mensualidad> findByVehiculoPlacaAndEstadoAndFechaFinGreaterThanEqual(
            String placa, EstadoMensualidad estado, LocalDate fecha);
}

