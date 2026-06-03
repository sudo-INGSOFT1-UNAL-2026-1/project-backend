USE erp_db;

-- =========================
-- ROLE / ROL
-- =========================
INSERT INTO role (name) VALUES
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
-- PERMISSION / PERMISO
-- =========================
INSERT INTO permission (name) VALUES
('GESTION_ROLES'),
('GESTION_VENTAS'),
('GESTION_COMPRAS'),
('GESTION_INVENTARIO');

-- =========================
-- ROLE - PERMISSION RELATION
-- =========================
-- ROLE - PERMISSION RELATION / RELACION ROL - PERMISO
-- =========================

-- ADMIN_EMPRESA
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'ADMIN_EMPRESA';
-- EMPLEADO_VENTAS
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'EMPLEADO_VENTAS'
AND p.name = 'GESTION_VENTAS';

-- EMPLEADO_COMPRAS
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'EMPLEADO_COMPRAS'
AND p.name = 'GESTION_COMPRAS';

-- EMPLEADO_INVENTARIO
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'EMPLEADO_INVENTARIO'
AND p.name = 'GESTION_INVENTARIO';

-- EMPLEADO_VENTAS_COMPRAS
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'EMPLEADO_VENTAS_COMPRAS'
AND p.name IN (
    'GESTION_VENTAS',
    'GESTION_COMPRAS'
);

-- EMPLEADO_VENTAS_INVENTARIO
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'EMPLEADO_VENTAS_INVENTARIO'
AND p.name IN (
    'GESTION_VENTAS',
    'GESTION_INVENTARIO'
);

-- EMPLEADO_COMPRAS_INVENTARIO
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'EMPLEADO_COMPRAS_INVENTARIO'
AND p.name IN (
    'GESTION_COMPRAS',
    'GESTION_INVENTARIO'
);

-- EMPLEADO_TOTAL
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.name = 'EMPLEADO_TOTAL'
AND p.name IN (
    'GESTION_VENTAS',
    'GESTION_COMPRAS',
    'GESTION_INVENTARIO'
);