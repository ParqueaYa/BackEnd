package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * CONFIGURACIÓN ACTUAL: SEGURIDAD DESACTIVADA
     * Todos los endpoints están abiertos para desarrollo.
     *
     * Cuando se quiera activar la seguridad, descomentar las líneas marcadas
     * y comentar la línea de permitAll().
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // ---- RUTAS SIEMPRE PÚBLICAS ----
                // .requestMatchers("/api/auth/**").permitAll()
                // .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-ui.html").permitAll()
                // .requestMatchers("/api/health").permitAll()

                // ---- RUTAS PROTEGIDAS (activar después) ----
                // .requestMatchers("/api/admin/**").hasRole("ADMIN")
                // .anyRequest().authenticated()

                // ---- TODO ABIERTO POR AHORA ----
                .anyRequest().permitAll()
            );

        // ---- ACTIVAR FILTRO JWT DESPUÉS ----
        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

