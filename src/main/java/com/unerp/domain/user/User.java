package com.unerp.domain.user;

import com.unerp.domain.user.state.ActiveState;
import com.unerp.domain.user.state.UserState;
import com.unerp.domain.user.state.InactiveState;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Transient
    private UserState state;

    @Column(name = "status")
    private String stateString;

    @Column(name = "role_id")
    private Integer roleId;

    public User(
            Integer id,
            String name,
            String email,
            String passwordHash,
            UserState state,
            Integer roleId
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.state = state;
        this.stateString = state.getState();
        this.roleId = roleId;
    }

    public User() {
    }

    @PostLoad
    private void UploadState() {
        if("activo".equalsIgnoreCase(this.stateString)) {
            this.state = new ActiveState();
        } else {
            this.state = new InactiveState();
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserState getState() {
        return state;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void activate() {
            this.state = this.state.activate();
    }

    public void deactivate() {
        this.state.deactivate();
    }
}
