package org.example.service;

import org.example.dto.UpdateUsuarioDTO;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public Usuario actualizar(Long id, UpdateUsuarioDTO dto) {
        Usuario usuario = obtenerPorId(id);
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setRol(dto.getRol());
        return usuarioRepository.save(usuario);
    }

    public Usuario cambiarEstado(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuario.setActivo(!usuario.getActivo());
        return usuarioRepository.save(usuario);
    }
}
