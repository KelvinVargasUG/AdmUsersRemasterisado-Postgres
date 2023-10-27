package com.kjvargas.admusersremasterisadopostgres.Services;

import com.kjvargas.admusersremasterisadopostgres.Repositories.IUsuarioRepository;
import com.kjvargas.admusersremasterisadopostgres.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UsuarioService {
    @Autowired
    IUsuarioRepository usuarioRepository;

    @Value("${user-email-admin}")
    private String emailAdmin;

    @Value("${user-password-admin}")
    private String passwordAdmin;

    @PostConstruct
    public void createAdminUser() {
        usuarioRepository.create_admin_user(    this.emailAdmin, this.passwordAdmin);
    }
}
