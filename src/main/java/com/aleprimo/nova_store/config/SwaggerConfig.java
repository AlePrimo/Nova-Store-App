package com.aleprimo.nova_store.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {
//    http://localhost:8080/swagger-ui/index.html

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("🔐 API REST - Pagina de e-commerce")
                        .description("""
            Esta API REST permite gestionar usuarios, autenticación y roles de manera segura, 
            utilizando Spring Boot, JWT, y buenas prácticas de arquitectura.

            Funcionalidades principales:
            - Registro y login de usuarios
            - Asignación y consulta de roles
            - Validaciones, paginación y más

            Ideal como plantilla base para aplicaciones web modernas.
            """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Alejandro Carullo")
                                .email("alejandrojuliancarullo@gmail.com")
                                .url("https://github.com/AlePrimo/Nova-Store-App")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}
