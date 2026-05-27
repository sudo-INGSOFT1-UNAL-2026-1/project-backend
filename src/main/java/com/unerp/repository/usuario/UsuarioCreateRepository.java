package com.unerp.repository.usuario;

import com.unerp.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioCreateRepository extends JpaRepository <Usuario, Integer> {
}
