package com.kjvargas.admusersremasterisadopostgres.Security.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario.Rol;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UsuarioSecurity implements UserDetails {

    @Setter
    private String email;

    @JsonIgnore
    @Setter
    private String password;

    @Setter
    private List<Rol> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();
        for (Rol rol : this.roles) {
            autoridades.add(new Authority(rol.getNombre()));
        }
        return autoridades;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
