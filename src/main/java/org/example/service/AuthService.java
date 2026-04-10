package org.example.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.example.dto.*;
import org.example.model.Usuario;
import org.example.model.enums.AuthProvider;
import org.example.model.enums.Rol;
import org.example.repository.UsuarioRepository;
import org.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${google.clientId}")
    private String googleClientId;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registro de usuario con email y contraseña
     */
    public AuthResponseDTO registrar(RegistroUsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + dto.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setAuthProvider(AuthProvider.LOCAL);

        if (dto.getRol() != null && !dto.getRol().isBlank()) {
            try {
                usuario.setRol(Rol.valueOf(dto.getRol().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Rol no válido: " + dto.getRol() + ". Usa: ADMIN, OPERADOR o CLIENTE");
            }
        } else {
            usuario.setRol(Rol.OPERADOR);
        }

        usuario = usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name(), usuario.getId());

        return buildResponse(token, usuario);
    }

    /**
     * Login con email y contraseña
     */
    public AuthResponseDTO login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (usuario.getAuthProvider() == AuthProvider.GOOGLE) {
            throw new RuntimeException("Esta cuenta fue registrada con Google. Por favor inicia sesión con Google.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        if (!usuario.getActivo()) {
            throw new RuntimeException("La cuenta está desactivada");
        }

        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name(), usuario.getId());

        return buildResponse(token, usuario);
    }

    /**
     * Autenticación con Google.
     * El frontend envía el ID Token de Google, lo verificamos contra los servidores de Google,
     * y creamos/actualizamos el usuario automáticamente.
     */
    public AuthResponseDTO loginGoogle(GoogleAuthDTO dto) {
        GoogleIdToken.Payload payload = verifyGoogleToken(dto.getIdToken());

        String email = payload.getEmail();
        String googleId = payload.getSubject();
        String nombre = (String) payload.get("given_name");
        String apellido = (String) payload.get("family_name");
        String fotoUrl = (String) payload.get("picture");

        // Buscar o crear usuario
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null) {
            // Crear nuevo usuario de Google
            usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNombre(nombre != null ? nombre : "Usuario");
            usuario.setApellido(apellido);
            usuario.setGoogleId(googleId);
            usuario.setFotoUrl(fotoUrl);
            usuario.setAuthProvider(AuthProvider.GOOGLE);
            usuario.setRol(Rol.OPERADOR);
            usuario.setActivo(true);
        } else {
            // Actualizar datos de Google
            if (usuario.getAuthProvider() == AuthProvider.LOCAL) {
                throw new RuntimeException("Esta cuenta ya existe con usuario/contraseña. "
                        + "Por favor inicia sesión con email y contraseña.");
            }
            usuario.setGoogleId(googleId);
            if (fotoUrl != null) usuario.setFotoUrl(fotoUrl);
            if (nombre != null) usuario.setNombre(nombre);
            if (apellido != null) usuario.setApellido(apellido);
        }

        usuario.setUltimoLogin(LocalDateTime.now());
        usuario = usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name(), usuario.getId());

        return buildResponse(token, usuario);
    }

    /**
     * Obtener perfil del usuario actual
     */
    public Usuario obtenerPerfil(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ---- Métodos privados ----

    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new RuntimeException("Token de Google inválido");
            }
            return idToken.getPayload();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar token de Google: " + e.getMessage());
        }
    }

    private AuthResponseDTO buildResponse(String token, Usuario usuario) {
        String nombreCompleto = usuario.getNombre();
        if (usuario.getApellido() != null && !usuario.getApellido().isBlank()) {
            nombreCompleto += " " + usuario.getApellido();
        }
        return new AuthResponseDTO(
                token,
                usuario.getId(),
                usuario.getEmail(),
                nombreCompleto,
                usuario.getRol().name(),
                usuario.getAuthProvider().name(),
                usuario.getFotoUrl()
        );
    }
}

