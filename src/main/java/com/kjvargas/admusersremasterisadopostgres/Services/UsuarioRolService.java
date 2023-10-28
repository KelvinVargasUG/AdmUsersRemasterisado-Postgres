package com.kjvargas.admusersremasterisadopostgres.Services;

import com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario.Usuario;
import com.kjvargas.admusersremasterisadopostgres.Repositories.IUsuarioRepository;
import com.kjvargas.admusersremasterisadopostgres.Security.Entitys.UsuarioSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioRolService {
    @Autowired
    IUsuarioRepository usuarioRepository;

    public UsuarioSecurity findByIdEmailLoad(String email) {
        Usuario usuario = usuarioRepository.findByIdEmail(email);
        if(usuario == null) {
            throw new RuntimeException("El usuario no existe");
        }
        UsuarioSecurity usuarioSecurity = new UsuarioSecurity();
        usuarioSecurity.setEmail(usuario.getEmail());
        usuarioSecurity.setPassword(usuario.getPassword());
        usuarioSecurity.setRoles(usuario.getRoles());
        return usuarioSecurity;
    }
}
