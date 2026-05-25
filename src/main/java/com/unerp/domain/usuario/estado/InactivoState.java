package com.unerp.domain.usuario.estado;

public class InactivoState extends EstadoUsuario {

    @Override
    public String getEstado() {
        return "Inactivo";
    }

    @Override
    public EstadoUsuario activar() {
        return new ActivoState();
    }

    @Override
    public EstadoUsuario desactivar() {
        throw new IllegalStateException("El usuario ya esta inactivo");
    }
}
