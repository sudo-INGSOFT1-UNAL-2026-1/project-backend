package com.unerp.domain.user;

import com.unerp.domain.user.state.UserState;

public class UserBuilder {

    private Integer id;
    private String name;
    private String email;
    private String passwordHash;
    private UserState state;
    private Integer roleId;


    public UserBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserBuilder setState(UserState state) {
        this.state = state;
        return this;
    }

    public UserBuilder setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public User build() {
        return new User(id, name, email, passwordHash, state, roleId);
    }
}
