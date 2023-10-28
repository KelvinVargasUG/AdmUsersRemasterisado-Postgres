package com.kjvargas.admusersremasterisadopostgres.Services;

import com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario.Rol;
import com.kjvargas.admusersremasterisadopostgres.Repositories.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private IRolRepository rolRepository;

    public List<Rol> findAllRoles() {
        List<Rol> roles = rolRepository.findAllRoles();
        if (roles.isEmpty()) {
            throw new RuntimeException("No hay roles");
        }
        return roles;
    }

    public List<Rol> findRolByUserId(Long id) {
        List<Rol> roles = this.rolRepository.findRolByUserId(id);
        if(roles.isEmpty())
            throw new RuntimeException("No hay roles");
        return roles;
    }

}
