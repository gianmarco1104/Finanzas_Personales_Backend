# 💰 Finanzas App - Backend API

> API RESTful robusta y escalable para la gestión de finanzas personales. Construida con **Spring Boot 4**, enfocada en seguridad, rendimiento y una experiencia de usuario fluida mediante procesos asíncronos.

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Security](https://img.shields.io/badge/Spring_Security-6-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)

## 📋 Tabla de Contenidos
- [Arquitectura y Características](#-arquitectura-y-características)
- [Tecnologías](#-tecnologías)
- [Pre-requisitos](#-pre-requisitos)
- [Configuración](#-configuración)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Documentación de la API](#-documentación-de-la-api)
- [Roadmap](#-roadmap)

---

## 🏗 Arquitectura y Características

Este backend implementa una arquitectura por capas (Controller-Service-Repository) con características avanzadas de nivel empresarial:

* **🔐 Autenticación Segura:**
    * Registro de usuarios con contraseñas hasheadas (`BCrypt`).
    * Verificación de cuenta vía **OTP (One-Time Password)** de 6 dígitos.
    * Cuentas inactivas (`enabled=false`) hasta completar verificación.

* **⚡ Procesamiento Asíncrono (Non-blocking UI):**
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
