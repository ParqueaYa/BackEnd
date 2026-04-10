package org.example.service;

import org.example.dto.TipoVehiculoDTO;
import org.example.model.TipoVehiculo;
import org.example.repository.TipoVehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoVehiculoService {

    private final TipoVehiculoRepository tipoVehiculoRepository;

    public TipoVehiculoService(TipoVehiculoRepository tipoVehiculoRepository) {
        this.tipoVehiculoRepository = tipoVehiculoRepository;
    }

    public List<TipoVehiculo> listarTodos() {
        return tipoVehiculoRepository.findAll();
    }

    public TipoVehiculo obtenerPorId(Long id) {
        return tipoVehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de vehículo no encontrado con ID: " + id));
    }

    public TipoVehiculo crear(TipoVehiculoDTO dto) {
        if (tipoVehiculoRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new RuntimeException("Ya existe un tipo de vehículo con el nombre: " + dto.getNombre());
        }
        TipoVehiculo tipo = new TipoVehiculo(dto.getNombre().toUpperCase(), dto.getDescripcion());
        return tipoVehiculoRepository.save(tipo);
    }

    public TipoVehiculo actualizar(Long id, TipoVehiculoDTO dto) {
        TipoVehiculo tipo = obtenerPorId(id);
        tipo.setNombre(dto.getNombre().toUpperCase());
        tipo.setDescripcion(dto.getDescripcion());
        return tipoVehiculoRepository.save(tipo);
    }

    public void eliminar(Long id) {
        TipoVehiculo tipo = obtenerPorId(id);
        tipoVehiculoRepository.delete(tipo);
    }
}

