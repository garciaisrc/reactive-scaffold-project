-- ==============================
-- TABLA DE ROLES
-- ==============================
CREATE TABLE rol (
    id_rol BIGSERIAL PRIMARY KEY,
    name_rol VARCHAR(100) NOT NULL,
    description_rol VARCHAR(255)
);

-- ==============================
-- TABLA DE USUARIOS
-- ==============================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    num_document VARCHAR(50) UNIQUE NOT NULL,
    num_phone VARCHAR(20),
    id_rol BIGINT,
    base_salary NUMERIC(15,2),
    password VARCHAR(255) NOT NULL,
    CONSTRAINT fk_users_rol FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- ==============================
-- INSERTAR ROLES
-- ==============================
INSERT INTO rol (name_rol, description_rol)
VALUES 
('Administrador', 'Usuario con todos los permisos'),
('Asesor', 'Usuario con permisos limitados'),
('Cliente', 'Usuario con acceso restringido');
