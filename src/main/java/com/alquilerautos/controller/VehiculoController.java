
package com.alquilerautos.controller;

import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.service.VehiculoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    private final VehiculoService service;

    public VehiculoController(VehiculoService service) {
        this.service = service;
    }

    @PostMapping
    public Vehiculo registrar(@RequestBody Vehiculo v) {
        return service.save(v);
    }

    @GetMapping
    public List<Vehiculo> listar() {
        return service.getAll();
    }

    @GetMapping("/buscar")
    public List<Vehiculo> buscar(@RequestParam String placa) {
        return service.searchByPlaca(placa);
    }

    @PutMapping("/{id}")
    public Vehiculo editar(@PathVariable Long id, @RequestBody Vehiculo v) {
        return service.update(id, v);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.delete(id);
    }
}