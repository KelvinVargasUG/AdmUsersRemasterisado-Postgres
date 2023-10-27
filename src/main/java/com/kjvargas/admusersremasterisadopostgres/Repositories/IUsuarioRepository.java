package com.kjvargas.admusersremasterisadopostgres.Repositories;

import com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "CALL create_admin_user(:p_usuario, :p_password)", nativeQuery = true)
    public void create_admin_user(@Param("p_usuario") String emailAdmin,
                         @Param("p_password") String passwordAdmin);

}
