package com.unerp.controller.usuario;

import com.unerp.domain.usuario.Usuario;
import com.unerp.service.usuario.UsuarioCreateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioCreateController {

    private final UsuarioCreateService usuarioCreateService;

    public UsuarioCreateController(UsuarioCreateService usuarioCreateService) {
        this.usuarioCreateService = usuarioCreateService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(

            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String rolNombre
    ) {
        try {

            Usuario usuario = usuarioCreateService.crearUsuario(
                    nombre,
                    email,
                    password,
                    rolNombre
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor");
        }
    }
}
