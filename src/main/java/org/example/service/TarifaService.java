package org.example.service;

import org.example.dto.TarifaDTO;
import org.example.model.Tarifa;
import org.example.model.TipoVehiculo;
import org.example.model.enums.TipoTarifa;
import org.example.repository.TarifaRepository;
import org.example.repository.TipoVehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;
    private final TipoVehiculoRepository tipoVehiculoRepository;

    public TarifaService(TarifaRepository tarifaRepository, TipoVehiculoRepository tipoVehiculoRepository) {
        this.tarifaRepository = tarifaRepository;
        this.tipoVehiculoRepository = tipoVehiculoRepository;
    }

    public List<Tarifa> listarTodas() {
        return tarifaRepository.findAll();
    }

    public List<Tarifa> listarActivas() {
        return tarifaRepository.findByActivaTrue();
    }

    public Tarifa obtenerPorId(Long id) {
        return tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada con ID: " + id));
    }

    public List<Tarifa> listarPorTipo(String tipoTarifa) {
        TipoTarifa tipo = TipoTarifa.valueOf(tipoTarifa.toUpperCase());
        return tarifaRepository.findByTipoTarifa(tipo);
    }

    public Tarifa crear(TarifaDTO dto) {
        TipoTarifa tipoTarifa = TipoTarifa.valueOf(dto.getTipoTarifa().toUpperCase());
        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(dto.getTipoVehiculoId())
                .orElseThrow(() -> new RuntimeException("Tipo de vehículo no encontrado con ID: " + dto.getTipoVehiculoId()));

        // Verificar si ya existe una tarifa activa para esa combinación
        tarifaRepository.findByTipoTarifaAndTipoVehiculoIdAndActivaTrue(tipoTarifa, dto.getTipoVehiculoId())
                .ifPresent(existing -> {
                    throw new RuntimeException("Ya existe una tarifa activa de tipo " + tipoTarifa
                            + " para el tipo de vehículo " + tipoVehiculo.getNombre());
                });

        Tarifa tarifa = new Tarifa(tipoTarifa, tipoVehiculo, dto.getMonto());
        return tarifaRepository.save(tarifa);
    }

    public Tarifa actualizar(Long id, TarifaDTO dto) {
        Tarifa tarifa = obtenerPorId(id);
        TipoTarifa tipoTarifa = TipoTarifa.valueOf(dto.getTipoTarifa().toUpperCase());
        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(dto.getTipoVehiculoId())
                .orElseThrow(() -> new RuntimeException("Tipo de vehículo no encontrado con ID: " + dto.getTipoVehiculoId()));

        tarifa.setTipoTarifa(tipoTarifa);
        tarifa.setTipoVehiculo(tipoVehiculo);
        tarifa.setMonto(dto.getMonto());
        if (dto.getActiva() != null) {
            tarifa.setActiva(dto.getActiva());
        }
        return tarifaRepository.save(tarifa);
    }

    public void eliminar(Long id) {
        Tarifa tarifa = obtenerPorId(id);
        tarifaRepository.delete(tarifa);
    }
}

