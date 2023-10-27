
--Crea Database
--create database kjvargas;

CREATE OR REPLACE FUNCTION create_admin_user(p_usuario VARCHAR, p_password VARCHAR) RETURNS VOID AS $$
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

    -- Verifica si el usuario ya existe
SELECT id_usuario INTO v_id_usuario FROM usuario WHERE email = p_usuario;

-- Si el usuario no existe, créalo
IF v_id_usuario IS NULL THEN
        INSERT INTO usuario (fecha_creacion, fecha_modificacion, email, nombre, apellido, PASSWORD)
        VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, p_usuario, 'admin', 'rol', p_password)
        RETURNING ID_USUARIO INTO v_id_usuario;

        -- Asigna el ID_ROL al usuario
INSERT INTO usuario_rol (fecha_creacion, fecha_modificacion, id_rol, id_usuario)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, v_id_rol, v_id_usuario);
END IF;

    RETURN;
END;
$$ LANGUAGE plpgsql;

