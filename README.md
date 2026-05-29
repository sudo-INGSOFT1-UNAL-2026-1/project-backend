# Endpoints Backend UNERP

## Crear usuario

### Endpoint

```http
POST /user/create
```

### Tipo de Body

```text
x-www-form-urlencoded
```

### Parámetros

| Parameter | Type |
|---|---|
| name | String |
| email | String |
| password | String |
| roleName | String |

### Roles disponibles

- ADMIN_EMPRESA
- EMPLEADO_VENTAS
- EMPLEADO_COMPRAS
- EMPLEADO_INVENTARIO
- EMPLEADO_VENTAS_COMPRAS
- EMPLEADO_VENTAS_INVENTARIO
- EMPLEADO_COMPRAS_INVENTARIO
- EMPLEADO_TOTAL

## Nota

Si es el primer usuario del sistema, automáticamente se creará como:

```text
ADMIN_EMPRESA
```

### Ejemplo

```text
name=user
email=user@gmail.com
password=123456
roleName=EMPLEADO_TOTAL
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

| Parameter | Type |
|---|---|
| email | String |
| password | String |

### Ejemplo

```text
email=angel@gmail.com
password=123456
```

---

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
                │   ├── user
                │   │   └── UserCreateController.java
                │   │
                │   ├── sales
                │   ├── purchases
                │   └── inventory


                ├── service
                │
                │   ├── auth
                │   │   └── AuthLoginService.java
                │   │
                │   ├── user
                │   │   └── UserCreateService.java
                │   │
                │   ├── sales
                │   ├── purchases
                │   └── inventory


                ├── repository
                │
                │   ├── user
                │   │   ├── UserCreateRepository.java
                │   │   └── UserReadRepository.java
                │   │
                │   ├── role
                │   │   └── RoleReadRepository.java
                │   │
                │   ├── sales
                │   ├── purchases
                │   └── inventory


                ├── domain
                │
                │   ├── user
                │   │   ├── User.java
                │   │   ├── UserBuilder.java
                │   │   │
                │   │   └── state
                │   │       ├── UserState.java
                │   │       ├── ActiveState.java
                │   │       └── InactiveState.java
                │   │
                │   └── role
                │       ├── Role.java
                │       └── RoleName.java


                └── security
                    ├── PasswordHasher.java
                    └── SecurityConfig.java
```