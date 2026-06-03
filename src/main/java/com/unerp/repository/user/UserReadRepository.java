package com.unerp.repository.user;

import com.unerp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReadRepository extends JpaRepository <User, Integer> {

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
