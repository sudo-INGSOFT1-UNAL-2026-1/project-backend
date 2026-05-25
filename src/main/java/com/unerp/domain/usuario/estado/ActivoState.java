package com.unerp.domain.usuario.estado;

public class ActivoState extends EstadoUsuario {

    @Override
    public String getEstado() {
        return "Activo";
    }

    @Override
    public EstadoUsuario activar() {
        throw new IllegalStateException("El usuario ya esta activo");
    }

    @Override
    public EstadoUsuario desactivar() {
        return new InactivoState();
    }
}
