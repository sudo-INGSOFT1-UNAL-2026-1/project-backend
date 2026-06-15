package com.unerp.repository.user;

import com.unerp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWriteRepository extends JpaRepository<User, Integer> {}
