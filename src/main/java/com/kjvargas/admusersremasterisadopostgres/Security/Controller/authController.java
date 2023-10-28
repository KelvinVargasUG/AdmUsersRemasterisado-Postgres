package com.kjvargas.admusersremasterisadopostgres.Security.Controller;

import com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario.Usuario;
import com.kjvargas.admusersremasterisadopostgres.Security.ConfigurationsJwt.JwtUtils;
import com.kjvargas.admusersremasterisadopostgres.Security.Entitys.JwtRequest;
import com.kjvargas.admusersremasterisadopostgres.Security.SecuritySpring.EncryptToken;
import com.kjvargas.admusersremasterisadopostgres.Security.Entitys.UsuarioSecurity;
import com.kjvargas.admusersremasterisadopostgres.Security.Entitys.JwtResponse;
import com.kjvargas.admusersremasterisadopostgres.Security.SecuritySpring.UserDetailService;
import com.kjvargas.admusersremasterisadopostgres.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class authController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            autenticar(jwtRequest.getEmail(), jwtRequest.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        autenticar(jwtRequest.getEmail(), jwtRequest.getPassword());

        UserDetails userDetails = this.userDetailService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new RuntimeException("Usuario Deshabilitado " + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciales Invalidas");
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("El password es obligatorio");
            }
            return ResponseEntity.ok(usuarioService.createUser(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/actual-usuario")
    public ResponseEntity<?> obtenerUsuarioActual(Principal principal) {
        try {
            UsuarioSecurity usuario = (UsuarioSecurity) this.userDetailService.loadUserByUsername(principal.getName());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
