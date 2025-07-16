package com.aleprimo.nova_store.config;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("🛒 Nova Store API - Plataforma de E-commerce")
                        .description("""
                            Bienvenido a la documentación oficial de la **API REST de Nova Store**, una solución moderna de comercio electrónico construida con **Spring Boot**, **JWT**, **Spring Security** y buenas prácticas de arquitectura.

                            🔧 **Funcionalidades clave**:
                            - Gestión completa de productos y categorías
                            - CRUD de usuarios con validaciones y roles (admin/user)
                            - Registro, autenticación segura y cambio de contraseña
                            - Búsqueda avanzada, paginación y filtrado
                            - Documentación Swagger/OpenAPI

                            🔐 **Autenticación**:
                            Para acceder a los endpoints protegidos, primero realiza login y copia el token JWT.
                            Luego, presiona el botón **"Authorize"** e ingrésalo con el formato:
                            
                            ```
                            Bearer TU_TOKEN_AQUI
                            ```

                            Desarrollado con enfoque en escalabilidad, mantenibilidad y seguridad.
                        """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Alejandro Carullo")
                                .email("alejandrojuliancarullo@gmail.com")
                                .url("https://github.com/AlePrimo/Nova-Store-App")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components().addSecuritySchemes("JWT",
                        new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("Ingrese el token JWT como: `Bearer eyJhbGci...`")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}
