package com.unerp.service.usuario;

import com.unerp.security.PasswordHasher;
import com.unerp.domain.rol.Rol;
import com.unerp.domain.rol.RolNombre;
import com.unerp.domain.usuario.Usuario;
import com.unerp.domain.usuario.UsuarioBuilder;
import com.unerp.domain.usuario.estado.ActivoState;
import com.unerp.repository.usuario.RolReadRepository;
import com.unerp.repository.usuario.UsuarioCreateRepository;
import com.unerp.repository.usuario.UsuarioReadRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioCreateService {

    private final UsuarioReadRepository usuarioReadRepository;
    private final UsuarioCreateRepository usuarioCreateRepository;
    private final RolReadRepository rolReadRepository;
    private final PasswordHasher passwordHasher;

    public UsuarioCreateService(
            UsuarioReadRepository usuarioReadRepository,
            UsuarioCreateRepository usuarioCreateRepository,
            RolReadRepository rolReadRepository,
            PasswordHasher passwordHasher
    ) {
        this.usuarioReadRepository = usuarioReadRepository;
        this.usuarioCreateRepository = usuarioCreateRepository;
        this.passwordHasher = passwordHasher;
        this.rolReadRepository = rolReadRepository;
    }

    public Usuario crearUsuario(
            String nombre,
            String email,
            String password,
            String nombreRol
    ) {
        validarEmailDisponible(email);

        Rol rol = obtenerRol(nombreRol);

        String passwordHash = passwordHasher.hash(password);

        Usuario nuevoUsuario = new UsuarioBuilder()
                .setNombre(nombre)
                .setEmail(email)
                .setPasswordHash(passwordHash)
                .setEstado(new ActivoState())
                .setRolId(rol.getId())
                .build();

        return usuarioCreateRepository.save(nuevoUsuario);
    }

    private void validarEmailDisponible(String email) {
        if (usuarioReadRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
    }

    private Rol obtenerRol(String nombreRol) {

        Rol rol;

        if (usuarioReadRepository.count() != 0) {
            rol = rolReadRepository.findByNombre(nombreRol);
            if (rol == null) {
                throw new IllegalArgumentException("El rol especificado no existe");
        }
        return rol;
    }
        rol = rolReadRepository.findByNombre(RolNombre.ADMIN_EMPRESA);
        return rol;
    }
}