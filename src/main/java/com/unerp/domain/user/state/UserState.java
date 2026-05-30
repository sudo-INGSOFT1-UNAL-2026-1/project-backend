package com.unerp.domain.user.state;

public abstract class UserState {

    public abstract String getName();

    public abstract UserState activate();

    public abstract UserState deactivate();


}
