package com.unerp.domain.usuario;

import com.unerp.domain.usuario.estado.EstadoUsuario;

public class UsuarioBuilder {

    private Integer id;
    private String nombre;
    private String email;
    private String passwordHash;
    private EstadoUsuario estado;
    private Integer rolId;


    public UsuarioBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public UsuarioBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public UsuarioBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UsuarioBuilder setEstado(EstadoUsuario estado) {
        this.estado = estado;
        return this;
    }

    public UsuarioBuilder setRolId(Integer rolId) {
        this.rolId = rolId;
        return this;
    }

    public Usuario build() {
        return new Usuario(id, nombre, email, passwordHash, estado, rolId);
    }
}
