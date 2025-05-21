
package com.alquilerautos.service;

import com.alquilerautos.model.Pago;
import com.alquilerautos.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    private final PagoRepository repo;

    public PagoService(PagoRepository repo) {
        this.repo = repo;
    }

    public Pago save(Pago p) {
        return repo.save(p);
    }

    public List<Pago> getAll() {
        return repo.findAll();
    }

    public Pago update(Long id, Pago nuevo) {
        Pago p = repo.findById(id).orElseThrow();
        p.setReserva(nuevo.getReserva());
        p.setMonto(nuevo.getMonto());
        p.setFechaPago(nuevo.getFechaPago());
        p.setMetodo(nuevo.getMetodo());
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
