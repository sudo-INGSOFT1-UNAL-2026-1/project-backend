-- =========================
-- RESET
-- =========================
DROP DATABASE IF EXISTS erp_db;
CREATE DATABASE erp_db;
USE erp_db;

-- ROLE / ROL
CREATE TABLE role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- PERMISSION / PERMISO
CREATE TABLE permission (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- ROLE - PERMISSION RELATION / RELACION ROL - PERMISO
CREATE TABLE role_permission (
    role_id INT,
    permission_id INT,
    PRIMARY KEY (role_id, permission_id),

    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- USER / USUARIO
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    status ENUM('activo', 'inactivo') DEFAULT 'activo',
    role_id INT,

    FOREIGN KEY (role_id) REFERENCES role(id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- CUSTOMER / CLIENTE
CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255)
) ENGINE=InnoDB;

-- SUPPLIER / PROVEEDOR
CREATE TABLE supplier (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- PRODUCT / PRODUCTO
CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    stock INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    batch VARCHAR(50),
    expiration_date DATE,
    supplier_id INT,

    FOREIGN KEY (supplier_id) REFERENCES supplier(id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- SALE / VENTA
CREATE TABLE sale (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    user_id INT NOT NULL,
    payment_date DATE,
    delivery_date DATE,
    status ENUM('entregado', 'por enviar', 'en camino', 'cancelado') NOT NULL,
    total_cost DECIMAL(10,2) DEFAULT 0,

    FOREIGN KEY (customer_id) REFERENCES customer(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- SALE DETAIL / DETALLE VENTA
CREATE TABLE sale_product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sale_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2),

    FOREIGN KEY (sale_id) REFERENCES sale(id)
        ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;

-- PURCHASE / COMPRA
CREATE TABLE purchase (
    id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT NOT NULL,
    user_id INT NOT NULL,
    payment_date DATE,
    delivery_date DATE,
    status ENUM('pendiente', 'recibido', 'pagado', 'cancelado') NOT NULL,
    total_cost DECIMAL(10,2) DEFAULT 0,

    FOREIGN KEY (supplier_id) REFERENCES supplier(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- PURCHASE DETAIL / DETALLE COMPRA
CREATE TABLE purchase_product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    purchase_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2),

    FOREIGN KEY (purchase_id) REFERENCES purchase(id)
        ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;


-- SALE SUBTOTAL / SUBTOTAL VENTA
CREATE TRIGGER trg_sale_subtotal
BEFORE INSERT ON sale_product
FOR EACH ROW
SET NEW.subtotal = NEW.quantity * NEW.unit_price;

-- PURCHASE SUBTOTAL / SUBTOTAL COMPRA
CREATE TRIGGER trg_purchase_subtotal
BEFORE INSERT ON purchase_product
FOR EACH ROW
SET NEW.subtotal = NEW.quantity * NEW.unit_price;

-- SALE TOTAL / TOTAL VENTA
CREATE TRIGGER trg_sale_total
AFTER INSERT ON sale_product
FOR EACH ROW
UPDATE sale
SET total_cost = (
    SELECT SUM(subtotal)
    FROM sale_product
    WHERE sale_id = NEW.sale_id
)
WHERE id = NEW.sale_id;

-- PURCHASE TOTAL / TOTAL COMPRA
CREATE TRIGGER trg_purchase_total
AFTER INSERT ON purchase_product
FOR EACH ROW
UPDATE purchase
SET total_cost = (
    SELECT SUM(subtotal)
    FROM purchase_product
    WHERE purchase_id = NEW.purchase_id
)
WHERE id = NEW.purchase_id;