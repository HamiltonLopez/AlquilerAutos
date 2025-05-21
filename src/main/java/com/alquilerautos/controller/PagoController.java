
package com.alquilerautos.controller;

import com.alquilerautos.model.Pago;
import com.alquilerautos.service.PagoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @PostMapping
    public Pago registrar(@RequestBody Pago p) {
        return service.save(p);
    }

    @GetMapping
    public List<Pago> listar() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public Pago editar(@PathVariable Long id, @RequestBody Pago p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.delete(id);
    }
}
