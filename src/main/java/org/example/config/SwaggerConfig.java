package org.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ParqueaYa API")
                        .version("1.0")
                        .description(
                            "API REST para el sistema de gestión de parqueadero **ParqueaYa**.\n\n"
                            + "## Módulos\n"
                            + "- **Autenticación**: Registro, login con email/contraseña y login con Google\n"
                            + "- **Tipos de Vehículo**: CRUD de tipos (CARRO, MOTO, etc.)\n"
                            + "- **Vehículos**: CRUD y asociación con clientes\n"
                            + "- **Clientes**: CRUD de clientes del parqueadero\n"
                            + "- **Tarifas**: Configuración por hora, día y mensualidad\n"
                            + "- **Registro de Parqueo**: Ingreso/salida de vehículos, consulta de costos\n"
                            + "- **Mensualidades**: Gestión de planes mensuales\n"
                            + "- **Facturas**: Generación y consulta de facturas\n\n"
                            + "## Autenticación\n"
                            + "Los endpoints de `/api/auth/**` son públicos. "
                            + "Para los demás endpoints (cuando se active la seguridad), "
                            + "se debe enviar el JWT en el header:\n\n"
                            + "`Authorization: Bearer <token>`\n\n"
                            + "Usa el botón **Authorize** de arriba para ingresar tu token."
                        )
                        .contact(new Contact()
                                .name("Equipo ParqueaYa")
                                .email("soporte@parqueaya.com")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer JWT"))
                .components(new Components()
                        .addSecuritySchemes("Bearer JWT", new SecurityScheme()
                                .name("Bearer JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Ingresa tu JWT token. Obtenlo desde POST /api/auth/login o POST /api/auth/google")));
    }
}

