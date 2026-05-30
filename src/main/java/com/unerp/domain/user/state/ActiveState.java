package com.unerp.domain.user.state;

public class ActiveState extends UserState {

    @Override
    public String getName() {
        return "Activo";
    }

    @Override
    public UserState activate() {
        throw new IllegalStateException("El usuario ya esta activo");
    }

    @Override
    public UserState deactivate() {
        return new InactiveState();
    }
}
