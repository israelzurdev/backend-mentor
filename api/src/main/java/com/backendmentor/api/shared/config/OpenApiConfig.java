package com.backendmentor.api.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend Mentor API")
                        .version("v1.0.0")
                        .description("Documentación interactiva de la API REST para la plataforma de retos Backend Mentor")
                        .contact(new Contact()
                                .name("Israel Zurita")
                                .url("https://github.com/israelzuritadev-web/backend-mentor.git")));
    }
}
