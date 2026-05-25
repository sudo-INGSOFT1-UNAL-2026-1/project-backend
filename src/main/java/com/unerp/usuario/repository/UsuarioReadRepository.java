package com.unerp.usuario.repository;

import com.unerp.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioReadRepository extends JpaRepository <Usuario, Integer> {

    boolean existsByEmail(String email);

    Usuario findByEmail(String email);
}
