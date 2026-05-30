package com.unerp.domain.user.state;

public class InactiveState extends UserState {

    @Override
    public String getName() {
        return "Inactivo";
    }

    @Override
    public UserState activate() {
        return new ActiveState();
    }

    @Override
    public UserState deactivate() {
        throw new IllegalStateException("El usuario ya esta inactivo");
    }
}
