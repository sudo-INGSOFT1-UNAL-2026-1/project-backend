package com.unerp.domain.usuario;

import com.unerp.domain.usuario.estado.EstadoUsuario;

public class Usuario {

    private Integer id;
    private String nombre;
    private String email;
    private String passwordHash;
    private EstadoUsuario estado;
    private Integer rolId;

    public Usuario(
            Integer id,
            String nombre,
            String email,
            String passwordHash,
            EstadoUsuario estado,
            Integer rolId
    ) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.estado = estado;
        this.rolId = rolId;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void activar() {
            this.estado = this.estado.activar();
    }

    public void desactivar() {
        this.estado.desactivar();
    }
}
