package com.alquilerautos.service;

import com.alquilerautos.model.Usuario;
import com.alquilerautos.repository.UsuarioRepository;
import com.alquilerautos.exception.ResourceNotFoundException;
import com.alquilerautos.exception.ValidationException;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final Validator validator;

    public UsuarioService(UsuarioRepository repo, Validator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    private void validateUsuario(Usuario u) {
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }

        // Validar correo único
        List<Usuario> usuariosConCorreo = repo.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase("", u.getCorreo());
        if (!usuariosConCorreo.isEmpty() && 
            (u.getId() == null || !usuariosConCorreo.get(0).getId().equals(u.getId()))) {
            throw new ValidationException("Ya existe un usuario con este correo");
        }
    }

    public Usuario save(Usuario u) {
        validateUsuario(u);
        return repo.save(u);
    }

    public List<Usuario> getAll() {
        return repo.findAll();
    }

    public List<Usuario> search(String valor) {
        return repo.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(valor, valor);
    }

    public Usuario update(Long id, Usuario nuevo) {
        Usuario u = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        
        u.setNombre(nuevo.getNombre());
        u.setCorreo(nuevo.getCorreo());
        u.setRol(nuevo.getRol());
        u.setContrasena(nuevo.getContrasena());

        validateUsuario(u);
        return repo.save(u);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", "id", id);
        }
        repo.deleteById(id);
    }
}
