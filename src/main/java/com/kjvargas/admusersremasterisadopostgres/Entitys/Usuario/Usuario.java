package com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kjvargas.admusersremasterisadopostgres.Entitys.CamposObligatorios;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Usuario extends CamposObligatorios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank(message = "{app.fiel.notEmpty.error}")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "{app.fiel.notEmpty.error}")
    @Column(name = "apellido")
    private String apellido;

    @Email
    @NotBlank(message = "{app.fiel.notEmpty.error}")
    @Column(name = "email")
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "password")
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol"))
    private List<Rol> roles;

}
