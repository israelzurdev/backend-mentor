package com.backendmentor.api.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Login: busca al usuario por su email.
    // Optional porque si el correo no existe en la BD, no nos revienta la app con un NullPointerException.
    Optional<User> findByEmail(String email);

    // Perfiles: busca al usuario por su nombre de usuario para mostrar su perfil público.
    Optional<User> findByUsername(String username);

    // Registro: comprueba si el email ya está en uso.
    // Es un booleano (true/false) súper rápido: solo mira si existe sin cargar todos los datos en memoria.
    boolean existsByEmail(String email);

    // Registro: comprueba si el nombre de usuario ya está cogido por otra persona.
    boolean existsByUsername(String username);
}
