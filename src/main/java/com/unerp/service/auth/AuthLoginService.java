package com.unerp.service.auth;

import com.unerp.security.PasswordHasher;
import com.unerp.domain.usuario.Usuario;
import com.unerp.repository.usuario.UsuarioReadRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthLoginService {

    private final UsuarioReadRepository usuarioReadRepository;

    private final PasswordHasher passwordHasher;

    public AuthLoginService(
            UsuarioReadRepository usuarioReadRepository,
            PasswordHasher passwordHasher
    ) {
        this.usuarioReadRepository = usuarioReadRepository;
        this.passwordHasher = passwordHasher;
    }

    public Usuario login(String email, String password) {

        Usuario usuario = usuarioReadRepository.findByEmail(email);

        validarUsuarioExiste(usuario);

        validarPassword(password, usuario.getPasswordHash());

        validarUsuarioActivo(usuario);

        return usuario;
    }

    private void validarUsuarioExiste(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }
    }

    private void validarPassword(String password, String passwordHash) {
        if (!passwordHasher.matches(password, passwordHash)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
    }

    private void validarUsuarioActivo(Usuario usuario) {

        String estado = usuario.getEstado().getEstado();

        if (!estado.equals("Activo")) {
            throw new IllegalArgumentException("El usuario no esta activo");
        }
    }
}

