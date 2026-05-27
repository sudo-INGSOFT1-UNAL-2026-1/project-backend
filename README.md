# Endpoints Backend UNERP

## Crear usuario

### Endpoint

```http
POST /usuario/crear
```

### Tipo de Body

```text
x-www-form-urlencoded
```

### Parámetros

| Parámetro | Tipo |
|---|---|
| nombre | String |
| email | String |
| password | String |
| nombreRol | String |

### Roles disponibles

- ADMIN_EMPRESA
- EMPLEADO_VENTAS
- EMPLEADO_COMPRAS
- EMPLEADO_INVENTARIO
- EMPLEADO_VENTAS_COMPRAS
- EMPLEADO_VENTAS_INVENTARIO
- EMPLEADO_COMPRAS_INVENTARIO
- EMPLEADO_TOTAL

## Nota: Si es el primer usuario, se crea como ADMIN_EMPRESA

### Ejemplo

```text
nombre=user
email=user@gmail.com
password=123456
nombreRol=EMPLEADO_TOTAL
```

---

## Login

### Endpoint

```http
POST /auth/login
```

### Tipo de Body

```text
x-www-form-urlencoded
```

### Parámetros

| Parámetro | Tipo |
|---|---|
| email | String |
| password | String |

### Ejemplo

```text
email=angel@gmail.com
password=123456
```

# Estructura
```text
src
    └── main
        └── java
            └── com
                └── unerp
                    ├── controller
                    │
                    │   ├── auth
                    │   │   └── AuthLoginController.java
                    │   │
                    │   ├── usuario
                    │   │   └── UsuarioCreateController.java
                    │   │
                    │   ├── ventas
                    │   ├── compras
                    │   └── inventario


                    ├── service
                    │
                    │   ├── auth    
                    │   │   └── AuthLoginService.java
                    │   │
                    │   ├── usuario
                    │   │   └── UsuarioCreateService.java
                    │   │
                    │   ├── ventas
                    │   ├── compras
                    │   └── inventario
    

                    ├── repository
                    │
                    │   ├── usuario
                    │   │   ├── UsuarioCreateRepository.java
                    │   │   └── UsuarioReadRepository.java
                    │   │
                    │   ├── rol
                    │   │   └── RolReadRepository.java
                    │   │
                    │   ├── ventas
                    │   ├── compras
                    │   └── inventario
    

                    ├── domain
                    │
                    │   ├── usuario
                    │   │   ├── Usuario.java
                    │   │   ├── UsuarioBuilder.java
                    │   │   │
                    │   │   └── estado
                    │   │       ├── EstadoUsuario.java
                    │   │       ├── ActivoState.java
                    │   │       └── InactivoState.java
                    │   │
                    │   └── rol
                    │       ├── Rol.java
                    │       └── RolNombre.java


                    └── security
                        └── PasswordHasher.java
```