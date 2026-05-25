package com.unerp.usuario.service;

import com.unerp.auth.security.PasswordHasher;
import com.unerp.domain.usuario.Usuario;
import com.unerp.domain.usuario.UsuarioBuilder;
import com.unerp.domain.usuario.estado.ActivoState;
import com.unerp.usuario.repository.UsuarioCreateRepository;
import com.unerp.usuario.repository.UsuarioReadRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioCreateService {

    private final UsuarioReadRepository usuarioReadRepository;
    private final UsuarioCreateRepository usuarioCreateRepository;
    private final PasswordHasher passwordHasher;

    public UsuarioCreateService(
            UsuarioReadRepository usuarioReadRepository,
            UsuarioCreateRepository usuarioCreateRepository,
            PasswordHasher passwordHasher
    ) {
        this.usuarioReadRepository = usuarioReadRepository;
        this.usuarioCreateRepository = usuarioCreateRepository;
        this.passwordHasher = passwordHasher;
    }

    public Usuario crearUsuario(
            String nombre,
            String email,
            String password,
            Integer rolId
    ) {
        validarEmailDisponible(email);

        rolId = obtenerRolInicial(rolId);

        String passwordHash = passwordHasher.hash(password);

        Usuario nuevoUsuario = new UsuarioBuilder()
                .setNombre(nombre)
                .setEmail(email)
                .setPasswordHash(passwordHash)
                .setEstado(new ActivoState())
                .setRolId(rolId)
                .build();

        return usuarioCreateRepository.save(nuevoUsuario);
    }

    private void validarEmailDisponible(String email) {
        if (usuarioReadRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
    }

    private Integer obtenerRolInicial(Integer rolId) {
        if (usuarioReadRepository.count() == 0) {
            return 1;
        }
        return rolId;
    }
}