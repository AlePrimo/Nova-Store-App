
# 🛍️ Nova Store App - Backend

Este proyecto es un backend completo para un sistema de e-commerce, desarrollado con Java y Spring Boot. Incluye gestión de usuarios, productos, órdenes, pagos, carritos de compra, reviews, historial de compras y más.

## 🚀 Tecnologías Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security (JWT)
- Hibernate
- MySQL
- Maven
- Swagger (OpenAPI)
- Docker (opcional para despliegue)
- JUnit & Mockito (tests)

## 🧩 Módulos Principales

- **User & Role**: Registro, login, autenticación JWT y autorización.
- **Product & Category**: CRUD con paginación, stock, imágenes, descripciones.
- **Customer**: Perfil del cliente y datos personales.
- **Order & OrderItem**: Gestión de pedidos y sus ítems.
- **Payment**: Métodos y estado de pagos con enums.
- **Shipping**: Estado y detalles del envío.
- **ShoppingCart & CartItem**: Carrito persistente por usuario.
- **Invoice**: Generación de facturas por orden.
- **Review**: Opiniones y valoraciones de productos.
- **Wishlist**: Lista de deseos de cada cliente.
- **Purchase History**: Consulta de historial de compras.

## 📂 Estructura del Proyecto

```
src/main/java/com/nova/store/
├── controllers/         # Controladores REST
├── services/            # Interfaces de servicios
├── services/impl/       # Implementaciones de servicios
├── repositories/        # Repositorios JPA
├── daos/                # DAOs personalizados
├── daos/impl/           # Implementaciones DAO
├── dtos/                # DTOs (Request / Response)
├── entities/            # Entidades JPA
├── mappers/             # Mappers con MapStruct
└── config/              # Seguridad, Swagger, etc.
```

## ✅ Funcionalidades Clave

- Seguridad con JWT y roles.
- Validaciones con Jakarta.
- Documentación Swagger lista para usar.
- DTOs desacoplados de entidades.
- Servicios separados de lógica.
- Paginación en endpoints.
- Tests unitarios y de integración.

## 📦 Cómo Ejecutar

1. Clonar el repositorio
2. Crear base de datos `nova_store`
3. Configurar `application.yml`
4. Ejecutar con Maven o desde tu IDE

```bash
mvn spring-boot:run
```

## 🧪 Tests

```bash
mvn test
```

## 📄 Licencia

MIT

---

Desarrollado por [Alejandro Primo]
