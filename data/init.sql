-- =========================
-- RESET
-- =========================
DROP DATABASE IF EXISTS sistema_gestion;
CREATE DATABASE sistema_gestion;
USE sistema_gestion;

-- =========================
-- ROL
-- =========================
CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- =========================
-- PERMISO
-- =========================
CREATE TABLE permiso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- =========================
-- RELACION ROL - PERMISO
-- =========================
CREATE TABLE rol_permiso (
    rol_id INT,
    permiso_id INT,
    PRIMARY KEY (rol_id, permiso_id),

    FOREIGN KEY (rol_id) REFERENCES rol(id) ON DELETE CASCADE,
    FOREIGN KEY (permiso_id) REFERENCES permiso(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- =========================
-- USUARIO
-- =========================
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    estado ENUM('activo', 'inactivo') DEFAULT 'activo',
    rol_id INT,

    FOREIGN KEY (rol_id) REFERENCES rol(id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================
-- CLIENTE
-- =========================
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255)
) ENGINE=InnoDB;

-- =========================
-- PROVEEDOR
-- =========================
CREATE TABLE proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- =========================
-- PRODUCTO
-- =========================
CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    stock INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    lote VARCHAR(50),
    fecha_vencimiento DATE,
    proveedor_id INT,

    FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================
-- VENTA
-- =========================
CREATE TABLE venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    usuario_id INT NOT NULL,
    fecha_pago DATE,
    fecha_entrega DATE,
    estado ENUM('entregado', 'por enviar', 'en camino', 'cancelado') NOT NULL,
    costo_total DECIMAL(10,2) DEFAULT 0,

    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================
-- DETALLE VENTA
-- =========================
CREATE TABLE venta_producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2),

    FOREIGN KEY (venta_id) REFERENCES venta(id)
        ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =========================
-- COMPRA
-- =========================
CREATE TABLE compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proveedor_id INT NOT NULL,
    usuario_id INT NOT NULL,
    fecha_pago DATE,
    fecha_entrega DATE,
    estado ENUM('pendiente', 'recibido', 'pagado', 'cancelado') NOT NULL,
    costo_total DECIMAL(10,2) DEFAULT 0,

    FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================
-- DETALLE COMPRA
-- =========================
CREATE TABLE compra_producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    compra_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2),

    FOREIGN KEY (compra_id) REFERENCES compra(id)
        ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =========================
-- TRIGGERS (SIN DELIMITER)
-- =========================

-- SUBTOTAL VENTA
CREATE TRIGGER trg_subtotal_venta
BEFORE INSERT ON venta_producto
FOR EACH ROW
SET NEW.subtotal = NEW.cantidad * NEW.precio_unitario;

-- SUBTOTAL COMPRA
CREATE TRIGGER trg_subtotal_compra
BEFORE INSERT ON compra_producto
FOR EACH ROW
SET NEW.subtotal = NEW.cantidad * NEW.precio_unitario;

-- TOTAL VENTA
CREATE TRIGGER trg_total_venta
AFTER INSERT ON venta_producto
FOR EACH ROW
UPDATE venta
SET costo_total = (
    SELECT SUM(subtotal)
    FROM venta_producto
    WHERE venta_id = NEW.venta_id
)
WHERE id = NEW.venta_id;

-- TOTAL COMPRA
CREATE TRIGGER trg_total_compra
AFTER INSERT ON compra_producto
FOR EACH ROW
UPDATE compra
SET costo_total = (
    SELECT SUM(subtotal)
    FROM compra_producto
    WHERE compra_id = NEW.compra_id
)
WHERE id = NEW.compra_id;