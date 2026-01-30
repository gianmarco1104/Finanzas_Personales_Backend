# 💰 Finanzas App - Backend API

> API RESTful robusta y escalable para la gestión de finanzas personales. Construida con **Spring Boot 4**.

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Security](https://img.shields.io/badge/Spring_Security-6-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)

---

## 🏗 Arquitectura y Características

Este backend implementa una arquitectura por capas (**Controller-Service-Repository**), separando claramente las responsabilidades para facilitar el mantenimiento y la escalabilidad.

### 📂 Estructura de Directorios
El proyecto sigue una organización modular y semántica:

```text
src/main/java/finances_practice/gmejia
├── config       # Configuraciones de Spring
├── controller   # Capa de Controladores (Endpoints REST)
├── dto          # Data Transfer Objects (Request/Response para desacoplar la BD)
├── entity       # Entidades JPA (Mapeo directo a tablas de PostgreSQL)
├── exception    # Manejo centralizado de errores (GlobalExceptionHandler)
├── mapper       # Interfaces de mapeo automático con MapStruct
├── repository   # Interfaces de acceso a datos (Spring Data JPA)
├── security     # Configuración de Seguridad (Filtros, JWT, UserDetailsService)
├── service      # Lógica de negocio, reglas y transacciones
└── utils        # Clases utilitarias
```

* **🔐 Autenticación Segura:**
    * Registro de usuarios con contraseñas hasheadas (`BCrypt`).
    * Verificación de cuenta vía **OTP (One-Time Password)** de 6 dígitos.
    * Cuentas inactivas (`enabled=false`) hasta completar verificación.

* **⚡ Procesamiento Asíncrono:**
    * Uso de `@Async` para el envío de correos electrónicos.

* **📧 Motor de Plantillas de Correo:**
    * Correos HTML dinámicos renderizados con **Thymeleaf**.
    * Diseño responsive y moderno (Estilo Card).

* **🛡️ Manejo de Errores:**
    * `GlobalExceptionHandler` para respuestas JSON estandarizadas.
    * Validaciones de DTOs (`@Valid`, `@NotBlank`, etc.).

---

## 🛠 Tecnologías

* **Core:** Java 21+, Spring Boot 4.0.2
* **Base de Datos:** PostgreSQL (Supabase)
* **ORM:** Spring Data JPA / Hibernate
* **Seguridad:** Spring Security 6
* **Mapeo:** MapStruct (para DTO <-> Entity)
* **Utilidades:** Lombok
* **Email:** JavaMailSender + Thymeleaf (Templates)
* **Build Tool:** Maven
