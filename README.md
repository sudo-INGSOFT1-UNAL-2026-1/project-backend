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
