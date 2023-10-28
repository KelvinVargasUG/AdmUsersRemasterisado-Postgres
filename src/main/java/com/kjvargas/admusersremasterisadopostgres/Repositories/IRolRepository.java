package com.kjvargas.admusersremasterisadopostgres.Repositories;

import com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {

    @Query(value = "SELECT " +
                    "r.id_rol," +
                    "r.nombre," +
                    "r.descripcion," +
                    "r.estado," +
                    "r.fecha_creacion," +
                    "r.fecha_modificacion" +
                    " FROM find_all_rol() r", nativeQuery = true)
    public List<Rol> findAllRoles();


    @Query(value = "SELECT " +
            "r.id_rol," +
            "r.nombre," +
            "r.descripcion," +
            "r.estado," +
            "r.fecha_creacion," +
            "r.fecha_modificacion" +
            " FROM find_all_rol_by_id(:id_user) r", nativeQuery = true)
    public List<Rol> findRolByUserId(@Param("id_user") Long id);
}
