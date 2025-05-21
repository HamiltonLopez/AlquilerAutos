
package com.alquilerautos.controller;

import com.alquilerautos.model.Reserva;
import com.alquilerautos.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @PostMapping
    public Reserva registrar(@RequestBody Reserva r) {
        return service.save(r);
    }

    @GetMapping
    public List<Reserva> listar() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public Reserva editar(@PathVariable Long id, @RequestBody Reserva r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.delete(id);
    }
}