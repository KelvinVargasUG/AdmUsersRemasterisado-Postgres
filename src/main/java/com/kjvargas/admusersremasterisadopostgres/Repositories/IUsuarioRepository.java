package com.kjvargas.admusersremasterisadopostgres.Repositories;

import com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value="select id_usuario, " +
            "nombre, " +
            "apellido, " +
            "email, " +
            "password, " +
            "estado, " +
            "fecha_creacion, " +
            "fecha_modificacion " +
            "from find_all_users()", nativeQuery = true)
    List<Usuario> find_all_users();

    @Query(value="select id_usuario, " +
            "nombre, " +
            "apellido, " +
            "email, " +
            "password, " +
            "estado, " +
            "fecha_creacion, " +
            "fecha_modificacion " +
            "from find_users_by_id(:id_user)", nativeQuery = true)
    public Usuario findByIdUser(@Param("id_user") Long id);

    @Query(value="select id_usuario, " +
            "nombre, " +
            "apellido, " +
            "email, " +
            "password, " +
            "estado, " +
            "fecha_creacion, " +
            "fecha_modificacion " +
            "from createusuario(:p_nombre,:p_apellido,:p_email,:p_password)", nativeQuery = true)
    public Usuario createUser(@Param("p_nombre")String nombre,
                              @Param("p_apellido")String apellido,
                              @Param("p_email")String email,
                              @Param("p_password")String password);

    @Query(value="select id_usuario, " +
            "nombre, " +
            "apellido, " +
            "email, " +
            "password, " +
            "estado, " +
            "fecha_creacion, " +
            "fecha_modificacion " +
            "from find_user_by_email(:email_user)", nativeQuery = true)
    public Usuario findByIdEmail(@Param("email_user")String email);


    @Query(value = "select deleteusuario(:id)", nativeQuery = true)
    public int deleteUserById(@Param("id") Long id);

    @Query(value = "select create_admin_user(:p_usuario, :p_password)",nativeQuery = true)
    int createUserAdmin(@Param("p_usuario") String emailAdmin, @Param("p_password") String passwordAdmin);

    @Query(value = "select habilitar_usuario(:id_user)", nativeQuery = true)
    int habilitarUsuario(@Param("id_user") Long idUser);
}
