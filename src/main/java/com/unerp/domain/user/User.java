package com.unerp.domain.user;

import com.unerp.domain.role.Role;
import com.unerp.domain.user.state.ActiveState;
import com.unerp.domain.user.state.InactiveState;
import com.unerp.domain.user.state.UserState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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

  @Transient private UserState state;

  @Column(name = "status")
  private String stateString;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  public User(
      Integer id, String name, String email, String passwordHash, UserState state, Role role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.state = state;
    this.stateString = state.getName();
    this.role = role;
  }

  public User() {}

  @PostLoad
  private void uploadState() {
    if ("activo".equalsIgnoreCase(this.stateString)) {
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

  public Role getRole() {
    return role;
  }

  public void activate() {
    this.state = this.state.activate();
    this.stateString = this.state.getName();
  }

  public void deactivate() {
    this.state = this.state.deactivate();
    this.stateString = this.state.getName();
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
