USE sistema_gestion;

-- =========================
-- ROLES
-- =========================
INSERT INTO rol (nombre) VALUES
('ADMIN_EMPRESA'),
('EMPLEADO_VENTAS'),
('EMPLEADO_COMPRAS'),
('EMPLEADO_INVENTARIO'),
('EMPLEADO_VENTAS_COMPRAS'),
('EMPLEADO_VENTAS_INVENTARIO'),
('EMPLEADO_COMPRAS_INVENTARIO'),
('EMPLEADO_TOTAL');

-- =========================
-- PERMISOS
-- =========================
INSERT INTO permiso (nombre) VALUES
('GESTION_USUARIOS'),
('GESTION_ROLES'),
('GESTION_VENTAS'),
('GESTION_COMPRAS'),
('GESTION_INVENTARIO');

-- =========================
-- RELACION ROL - PERMISO
-- =========================

-- ADMIN_EMPRESA
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM rol r, permiso p
WHERE r.nombre = 'ADMIN_EMPRESA';

-- EMPLEADO_VENTAS
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM rol r, permiso p
WHERE r.nombre = 'EMPLEADO_VENTAS'
AND p.nombre = 'GESTION_VENTAS';

-- EMPLEADO_COMPRAS
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM rol r, permiso p
WHERE r.nombre = 'EMPLEADO_COMPRAS'
AND p.nombre = 'GESTION_COMPRAS';

-- EMPLEADO_INVENTARIO
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM rol r, permiso p
WHERE r.nombre = 'EMPLEADO_INVENTARIO'
AND p.nombre = 'GESTION_INVENTARIO';

-- EMPLEADO_VENTAS_COMPRAS
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM rol r, permiso p
WHERE r.nombre = 'EMPLEADO_VENTAS_COMPRAS'
AND p.nombre IN (
    'GESTION_VENTAS',
    'GESTION_COMPRAS'
);

-- EMPLEADO_VENTAS_INVENTARIO
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM rol r, permiso p
WHERE r.nombre = 'EMPLEADO_VENTAS_INVENTARIO'
AND p.nombre IN (
    'GESTION_VENTAS',
    'GESTION_INVENTARIO'
);

-- EMPLEADO_COMPRAS_INVENTARIO
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM rol r, permiso p
WHERE r.nombre = 'EMPLEADO_COMPRAS_INVENTARIO'
AND p.nombre IN (
    'GESTION_COMPRAS',
    'GESTION_INVENTARIO'
);

-- EMPLEADO_TOTAL
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM rol r, permiso p
WHERE r.nombre = 'EMPLEADO_TOTAL'
AND p.nombre IN (
    'GESTION_VENTAS',
    'GESTION_COMPRAS',
    'GESTION_INVENTARIO'
);