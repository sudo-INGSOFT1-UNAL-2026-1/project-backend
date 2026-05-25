package com.unerp.domain.usuario.estado;

public abstract class EstadoUsuario {

    public abstract String getEstado();

    public abstract EstadoUsuario activar();

    public abstract EstadoUsuario desactivar();


}
