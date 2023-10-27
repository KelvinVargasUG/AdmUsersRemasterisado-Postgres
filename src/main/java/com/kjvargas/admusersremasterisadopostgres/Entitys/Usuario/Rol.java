package com.kjvargas.admusersremasterisadopostgres.Entitys.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kjvargas.admusersremasterisadopostgres.Entitys.CamposObligatorios;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "rol", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class Rol extends CamposObligatorios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "descripcion")
    private String descripcion;

}