package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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
                        .description("API REST para el sistema de gestión de parqueadero ParqueaYa. "
                                + "Permite gestionar vehículos, clientes, tarifas, registros de parqueo, "
                                + "mensualidades y facturación.")
                        .contact(new Contact()
                                .name("Equipo ParqueaYa")
                                .email("soporte@parqueaya.com")));
    }
}

