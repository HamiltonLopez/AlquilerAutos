
package com.alquilerautos.controller;

import com.alquilerautos.model.Usuario;
import com.alquilerautos.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public Usuario registrar(@RequestBody Usuario u) {
        return service.save(u);
    }

    @GetMapping
    public List<Usuario> listar() {
        return service.getAll();
    }

    @GetMapping("/buscar")
    public List<Usuario> buscar(@RequestParam String valor) {
        return service.search(valor);
    }

    @PutMapping("/{id}")
    public Usuario editar(@PathVariable Long id, @RequestBody Usuario u) {
        return service.update(id, u);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.delete(id);
    }
}