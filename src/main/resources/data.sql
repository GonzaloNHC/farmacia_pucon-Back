-- ===========================
-- Permisos
-- ===========================
INSERT INTO permissions (id, nombre, codigo) VALUES
 (1, 'Crear usuario', 'USUARIO_CREAR'),
 (2, 'Ver usuarios', 'USUARIO_VER'),
 (3, 'Editar usuario', 'USUARIO_EDITAR'),
 (4, 'Eliminar usuario', 'USUARIO_ELIMINAR'),
 (5, 'Crear paciente', 'PACIENTE_CREAR'),
 (6, 'Ver pacientes', 'PACIENTE_VER'),
 (7, 'Realizar venta', 'VENTA_REALIZAR'),
 (8, 'Ver bodega', 'BODEGA_VER'),
 (9, 'Editar bodega', 'BODEGA_EDITAR');

-- ===========================
-- Roles
-- ===========================
INSERT INTO roles (id, nombre, descripcion) VALUES
 (1, 'ADMINISTRADOR', 'Acceso total'),
 (2, 'VENDEDOR', 'Realiza ventas'),
 (3, 'BODEGUERO', 'Gestiona bodega');

-- ===========================
-- Relación roles-permisos
-- ===========================
INSERT INTO role_permissions (role_id, permission_id) VALUES
 (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),
 (2,7),
 (3,8),(3,9);

-- ===========================
-- NOTA: No se insertan usuarios ni usuario_roles
-- para evitar conflictos de IDs y permitir crear usuarios desde la API
-- ===========================
