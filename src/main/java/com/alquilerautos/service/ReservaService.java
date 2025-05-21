
package com.alquilerautos.service;

import com.alquilerautos.model.Reserva;
import com.alquilerautos.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository repo;

    public ReservaService(ReservaRepository repo) {
        this.repo = repo;
    }

    public Reserva save(Reserva r) {
        return repo.save(r);
    }

    public List<Reserva> getAll() {
        return repo.findAll();
    }

    public Reserva update(Long id, Reserva nueva) {
        Reserva r = repo.findById(id).orElseThrow();
        r.setUsuario(nueva.getUsuario());
        r.setVehiculo(nueva.getVehiculo());
        r.setFechaInicio(nueva.getFechaInicio());
        r.setFechaFin(nueva.getFechaFin());
        r.setEstado(nueva.getEstado());
        return repo.save(r);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
