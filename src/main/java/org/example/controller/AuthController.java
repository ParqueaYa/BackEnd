package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.model.Usuario;
import org.example.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de registro, login y autenticación con Google")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario",
            description = "Crea un usuario con email y contraseña. Roles disponibles: ADMIN, OPERADOR, CLIENTE. "
                    + "Si no se envía rol, se asigna OPERADOR por defecto.")
    public ResponseEntity<AuthResponseDTO> registrar(@Valid @RequestBody RegistroUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login con email y contraseña",
            description = "Autentica al usuario y devuelve un JWT token.")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/google")
    @Operation(summary = "Login / Registro con Google",
            description = "El frontend envía el ID Token obtenido de Google Sign-In. "
                    + "Si el usuario no existe, se crea automáticamente. "
                    + "Devuelve un JWT token del sistema.")
    public ResponseEntity<AuthResponseDTO> loginGoogle(@Valid @RequestBody GoogleAuthDTO dto) {
        return ResponseEntity.ok(authService.loginGoogle(dto));
    }

    @GetMapping("/perfil")
    @Operation(summary = "Obtener perfil del usuario actual",
            description = "Enviar el email como parámetro. "
                    + "Cuando se active la seguridad, se obtendrá del token automáticamente.")
    public ResponseEntity<Usuario> obtenerPerfil(@RequestParam String email) {
        return ResponseEntity.ok(authService.obtenerPerfil(email));
    }
}

