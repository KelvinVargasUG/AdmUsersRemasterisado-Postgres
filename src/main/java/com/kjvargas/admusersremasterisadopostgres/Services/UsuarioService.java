package com.kjvargas.admusersremasterisadopostgres.Services;

import com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario.Usuario;
import com.kjvargas.admusersremasterisadopostgres.Repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    IUsuarioRepository usuarioRepository;

    @Value("${user-email-admin}")
    private String emailAdmin;

    @Value("${user-password-admin}")
    private String passwordAdmin;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostConstruct
    public void createAdminUser() {
        String passwordEncrypt = this.bCryptPasswordEncoder.encode(passwordAdmin);
        int status = usuarioRepository.createUserAdmin(this.emailAdmin, passwordEncrypt);
        if (status != 1) {
            throw new RuntimeException("No se pudo crear el usuario administrador");
        }
    }

    public List<Usuario> findAllUser() {
        List<Usuario> usuarios = usuarioRepository.find_all_users();
        if (usuarios.isEmpty()) {
            throw new RuntimeException("No hay usuarios");
        }
        return usuarios;
    }

    public Usuario findByIdUser(Long id) {
        Usuario usuario = usuarioRepository.findByIdUser(id);
        if (usuario == null) {
            throw new RuntimeException("El usuario no existe");
        }
        return usuario;
    }

    public Usuario findByIdEmail(String email) {
        Usuario usuario = usuarioRepository.findByIdEmail(email);
        if (usuario == null) {
            throw new RuntimeException("El usuario no existe");
        }
        return usuario;
    }

    public ResponseEntity<?> deleteUser(Long id) {
        Usuario usuario = usuarioRepository.findByIdUser(id);
        if (usuario == null) {
            throw new RuntimeException("El usuario no existe");
        }
        int status = usuarioRepository.deleteUserById(id);
        if (status != 1) {
            throw new RuntimeException("No se pudo eliminar el usuario");
        }
        return ResponseEntity.ok("Usuario eliminado");
    }

    public ResponseEntity<Usuario> createUser(Usuario usuario) {
        String email = usuario.getEmail();
        Usuario emailExist = usuarioRepository.findByIdEmail(email);
        if (emailExist != null) {
            if (emailExist.getEstado() == null) {
                this.usuarioRepository.habilitarUsuario(emailExist.getId());
                throw new RuntimeException("El email ya existe, se habilitó el usuario");
            }
            throw new RuntimeException("El correo electrónico ya existe");
        }
        String passwordEncrypt = this.bCryptPasswordEncoder.encode(usuario.getPassword());
        Usuario usuarioResult = usuarioRepository.createUser(usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                passwordEncrypt);
        return ResponseEntity.ok(usuarioResult);
    }
}
