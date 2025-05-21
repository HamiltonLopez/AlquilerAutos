package com.alquilerautos.service;

import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository repo;

    public VehiculoService(VehiculoRepository repo) {
        this.repo = repo;
    }

    public Vehiculo save(Vehiculo v) {
        return repo.save(v);
    }

    public List<Vehiculo> getAll() {
        return repo.findAll();
    }

    public List<Vehiculo> searchByPlaca(String placa) {
        return repo.findByPlacaContainingIgnoreCase(placa);
    }

    public Vehiculo update(Long id, Vehiculo nuevo) {
        Vehiculo v = repo.findById(id).orElseThrow();
        v.setMarca(nuevo.getMarca());
        v.setModelo(nuevo.getModelo());
        v.setAnio(nuevo.getAnio());
        v.setPlaca(nuevo.getPlaca());
        v.setTipo(nuevo.getTipo());
        return repo.save(v);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
