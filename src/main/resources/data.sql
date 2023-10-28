
--Crea Database
--create database kjvargas;

--crea admin user-----
CREATE OR REPLACE FUNCTION create_admin_user(p_usuario VARCHAR, p_password VARCHAR) RETURNS INTEGER AS $$
DECLARE
v_id_rol INTEGER;
    v_id_usuario INTEGER;
BEGIN
SELECT id_rol INTO v_id_rol FROM rol WHERE nombre = 'Rol_Admin';

IF v_id_rol IS NULL THEN
        INSERT INTO rol (fecha_creacion, fecha_modificacion, nombre, descripcion)
        VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Rol_Admin', 'Rol de administrador puede crear y eliminar usuarios')
        RETURNING ID_ROL INTO v_id_rol;
END IF;

SELECT id_rol INTO v_id_rol FROM rol WHERE nombre = 'Rol_User';

IF v_id_rol IS NULL THEN
        INSERT INTO rol (fecha_creacion, fecha_modificacion, nombre, descripcion)
        VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Rol_User', 'Rol con limitados permisos')
        RETURNING ID_ROL INTO v_id_rol;
END IF;

    -- Verifica si el usuario ya existe
SELECT id_usuario INTO v_id_usuario FROM usuario WHERE email = p_usuario;

-- Si the usuario no existe, créalo
IF v_id_usuario IS NULL THEN
        INSERT INTO usuario (fecha_creacion, fecha_modificacion, email, nombre, apellido, PASSWORD)
        VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, p_usuario, 'admin', 'rol', p_password)
        RETURNING ID_USUARIO INTO v_id_usuario;

        -- Asigna el ID_ROL al usuario
INSERT INTO usuario_rol (fecha_creacion, fecha_modificacion, id_rol, id_usuario)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, v_id_rol, v_id_usuario);
END IF;

    -- Return 1 to indicate success
RETURN 1;
END;
$$ LANGUAGE plpgsql;



--find_all_users-----
CREATE OR REPLACE FUNCTION find_all_users()
    RETURNS TABLE (
        id_usuario BIGINT,
        nombre VARCHAR,
        apellido VARCHAR,
        email VARCHAR,
		password VARCHAR,
        estado VARCHAR,
		fecha_creacion timestamp,
		fecha_modificacion timestamp
    ) AS $$
BEGIN
RETURN QUERY
SELECT U.id_usuario as id,
       U.nombre,
       U.apellido,
       U.email,
       U.password,
       U.estado,
       U.fecha_creacion,
       u.fecha_modificacion
FROM usuario U
         INNER JOIN usuario_rol ur ON U.id_usuario = ur.id_usuario
         INNER JOIN rol ON ur.id_rol = rol.id_rol
WHERE U.estado IS NOT NULL
ORDER BY U.id_usuario DESC;
END;
$$ LANGUAGE plpgsql;


--find user by id--
CREATE OR REPLACE FUNCTION find_users_by_id(p_id_user BIGINT)
    RETURNS TABLE (
        id_usuario BIGINT,
        nombre VARCHAR,
        apellido VARCHAR,
        email VARCHAR,
		password VARCHAR,
        estado VARCHAR,
		fecha_creacion timestamp,
		fecha_modificacion timestamp
    ) AS $$
BEGIN
RETURN QUERY
SELECT U.id_usuario as id,
       U.nombre,
       U.apellido,
       U.email,
       U.password,
       U.estado,
       U.fecha_creacion,
       u.fecha_modificacion
FROM usuario U
         INNER JOIN usuario_rol ur ON U.id_usuario = ur.id_usuario
         INNER JOIN rol ON ur.id_rol = rol.id_rol
WHERE U.estado IS NOT NULL
  and u.id_usuario = p_id_user
ORDER BY U.id_usuario DESC;
END;
$$ LANGUAGE plpgsql;

--find user by email--
   CREATE OR REPLACE FUNCTION find_user_by_email(p_email VARCHAR)
    RETURNS TABLE (
        id_usuario BIGINT,
        nombre VARCHAR,
        apellido VARCHAR,
        email VARCHAR,
		password VARCHAR,
        estado VARCHAR,
		fecha_creacion timestamp,
		fecha_modificacion timestamp
    ) AS $$
BEGIN
RETURN QUERY
SELECT U.id_usuario as id,
       U.nombre,
       U.apellido,
       U.email,
       U.password,
       U.estado,
       U.fecha_creacion,
       u.fecha_modificacion
FROM usuario U
WHERE U.estado IS NOT NULL
  and u.email = p_email;
END;
$$ LANGUAGE plpgsql;

--find All roles---
CREATE OR REPLACE Function find_all_rol()
RETURNS TABLE (
        id_rol BIGINT,
        nombre VARCHAR,
        descripcion VARCHAR,
        estado VARCHAR,
		fecha_creacion timestamp,
		fecha_modificacion timestamp
    ) AS $$
BEGIN
RETURN QUERY
SELECT
    r.id_rol,
    r.nombre,
    r.descripcion,
    r.estado,
    r.fecha_creacion,
    r.fecha_modificacion
FROM rol r
WHERE r.estado IS NOT NULL;
END;
$$ LANGUAGE plpgsql;



--findByUserAssignedRoles------------------------------------------------------------
CREATE OR REPLACE Function find_all_rol_by_id(p_id_user BIGINT)
RETURNS TABLE (
        id_rol BIGINT,
        nombre VARCHAR,
        descripcion VARCHAR,
        estado VARCHAR,
		fecha_creacion timestamp,
		fecha_modificacion timestamp
    ) AS $$
BEGIN
RETURN QUERY
SELECT
    r.id_rol,
    r.nombre,
    r.descripcion,
    r.estado,
    r.fecha_creacion,
    r.fecha_modificacion
FROM usuario_rol ur
         INNER JOIN rol R ON R.id_rol = ur.id_rol
WHERE ur.estado IS NOT NULL
  and ur.id_usuario = p_id_user;
END;
$$ LANGUAGE plpgsql;


--Delete usuario----------------------------------------------------------------------
CREATE OR REPLACE FUNCTION deleteusuario(p_id_usuario BIGINT) RETURNS INTEGER AS $$
BEGIN
UPDATE usuario
SET estado = NULL
WHERE id_usuario = p_id_usuario;

IF FOUND THEN
        RETURN 1;
ELSE
        RETURN 0;
END IF;
END;
$$ LANGUAGE plpgsql;

--create usuario--
   CREATE OR REPLACE FUNCTION createusuario(
    p_nombre VARCHAR,
    p_apellido VARCHAR,
    p_email VARCHAR,
    p_password VARCHAR
) RETURNS usuario AS $$
DECLARE
new_user usuario;
BEGIN
INSERT INTO usuario (nombre, apellido, email, password, fecha_creacion, fecha_modificacion)
VALUES (p_nombre, p_apellido, p_email, p_password, current_timestamp, current_timestamp)
    RETURNING * INTO new_user;

INSERT INTO usuario_rol (fecha_creacion, fecha_modificacion, id_rol, id_usuario)
VALUES (current_timestamp, current_timestamp, 2, new_user.id_usuario);

RETURN new_user;
END;
$$ LANGUAGE plpgsql;

   --habilitar usuario--
   CREATE OR REPLACE FUNCTION habilitar_usuario(p_id_user BIGINT) RETURNS INT AS $$
BEGIN
UPDATE usuario
SET estado = 'A'
WHERE id_usuario = p_id_user;

RETURN 1;
END;
$$ LANGUAGE plpgsql;