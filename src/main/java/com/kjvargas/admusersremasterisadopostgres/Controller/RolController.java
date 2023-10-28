package com.kjvargas.admusersremasterisadopostgres.Controller;

import com.kjvargas.admusersremasterisadopostgres.Services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/roles")
public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping
    @PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> findAllRol() {
        try {
            return ResponseEntity.ok(rolService.findAllRoles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> findRolByUserId(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.rolService.findRolByUserId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
