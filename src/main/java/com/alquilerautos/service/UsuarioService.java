
package com.alquilerautos.service;

import com.alquilerautos.model.Usuario;
import com.alquilerautos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario save(Usuario u) {
        return repo.save(u);
    }

    public List<Usuario> getAll() {
        return repo.findAll();
    }

    public List<Usuario> search(String valor) {
        return repo.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(valor, valor);
    }

    public Usuario update(Long id, Usuario nuevo) {
        Usuario u = repo.findById(id).orElseThrow();
        u.setNombre(nuevo.getNombre());
        u.setCorreo(nuevo.getCorreo());
        u.setRol(nuevo.getRol());
        u.setContrasena(nuevo.getContrasena());
        return repo.save(u);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
