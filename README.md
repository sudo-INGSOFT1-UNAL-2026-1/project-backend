# UNERP Backend

<p align="center">

![Java](https://img.shields.io/badge/Java-21-red?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge)

</p>

Backend del sistema **UNERP**, desarrollado para la asignatura **Ingeniería de Software I** de la **Universidad Nacional de Colombia**.

UNERP es un sistema ERP modular orientado a pequeñas y medianas empresas, diseñado para administrar usuarios, clientes, proveedores, inventario, compras y ventas mediante una API REST desarrollada con Spring Boot.

---

# Características

- Arquitectura por capas.
- API REST.
- Persistencia con MySQL.
- Autenticación mediante JWT.
- Validaciones con Jakarta Validation.
- Patrón State para el manejo de estados.
- DTOs para la comunicación entre cliente y servidor.
- Mappers para la transformación de entidades.
- Docker para el entorno de base de datos.
- Maven Wrapper incluido.
- Validación de calidad de código mediante:
    - Checkstyle
    - PMD
    - Spotless

---

# Tecnologías

| Tecnología | Versión |
|------------|----------|
| Java | 21 |
| Spring Boot | 3.x |
| Spring Security | 6.x |
| Spring Data JPA | 3.x |
| Maven | 3.x |
| MySQL | 8 |
| Docker | Latest |
| JWT | JJWT |
| Jakarta Validation | Included |

---

# Arquitectura

El proyecto sigue una arquitectura por capas para mantener una clara separación de responsabilidades.

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Database
```

Cada módulo organiza su lógica en:

- Controller
- Service
- Repository
- Domain
- DTO

---

# Estructura del proyecto

```
src
└── main
    ├── java
    │   └── com.unerp
    │       ├── controller
    │       ├── domain
    │       ├── dto
    │       ├── repository
    │       ├── security
    │       ├── service
    │       └── UnerpApplication
    │
    └── resources
        └── application.yml
```

---

# Módulos implementados

- Autenticación
- Gestión de Usuarios
- Gestión de Roles
- Clientes
- Proveedores
- Productos
- Compras
- Ventas

---

# Patrones de diseño

Durante el desarrollo del proyecto se utilizaron los siguientes patrones:

- State
- Repository
- DTO
- Mapper

---

# Instalación

## Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

Ingresar al proyecto:

```bash
cd backend
```

---

# Scripts de configuración

El proyecto incluye scripts para facilitar la configuración inicial del entorno.

- `setup.bat` — Windows.
- `setup.sh` — Linux y macOS.

> La funcionalidad de estos scripts será documentada una vez finalice su implementación.

---

# Base de datos

La aplicación utiliza **MySQL**.

Los scripts de inicialización de la base de datos se encuentran en:

```
data/
├── init.sql
└── seed.sql
```

---

# Ejecución

Una vez configurado el entorno, ejecutar la aplicación mediante Maven Wrapper.

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```


# API REST

Una vez iniciada la aplicación, la API estará disponible en:

```
http://localhost:8080
```

Algunos endpoints disponibles:

```
POST /auth/login
```

```
GET /user/all
```

```
POST /user/create
```

```
GET /product/all
```

```
POST /product/create
```

```
GET /supplier/all
```

```
POST /purchase/create
```

```
GET /purchase/all
```

---

# Autenticación

La autenticación se realiza mediante **JWT**.

Después de iniciar sesión se genera un token que deberá enviarse en las peticiones protegidas utilizando el encabezado:

```
Authorization: Bearer <token>
```

---

# Principios de desarrollo

Durante el desarrollo del proyecto se siguieron buenas prácticas de ingeniería de software:

- Clean Code.
- Principios SOLID.
- Separación de responsabilidades.
- Arquitectura modular.
- Uso de DTOs para desacoplar la capa de presentación del dominio.
- Validaciones de entrada mediante Jakarta Validation.

---

# Equipo de desarrollo

Proyecto desarrollado para la asignatura:

**Ingeniería de Software I**

**Universidad Nacional de Colombia**

Grupo de trabajo:

**sudo**
