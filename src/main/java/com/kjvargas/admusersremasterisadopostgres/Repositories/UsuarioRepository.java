package com.kjvargas.admusersremasterisadopostgres.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Repository
public class UsuarioRepository {

    @Autowired
    private EntityManager entityManager;

    public void createUserAdmin(String emailAdmin, String passwordAdmin) {
      //  String passwordEncrypt = this.bCryptPasswordEncoder.encode(passwordAdmin);

        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("create_admin_user")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

        storedProcedure.setParameter(1, emailAdmin);
        storedProcedure.setParameter(2, passwordAdmin);

        storedProcedure.execute();
    }
}
