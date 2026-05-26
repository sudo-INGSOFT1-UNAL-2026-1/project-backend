package com.unerp.usuario.repository;

import com.unerp.domain.rol.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolReadRepository extends JpaRepository <Rol, Integer>{

    Rol findByNombre(String nombre);
}
