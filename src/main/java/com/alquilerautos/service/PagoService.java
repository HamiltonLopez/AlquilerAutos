package com.alquilerautos.service;

import com.alquilerautos.model.Pago;
import com.alquilerautos.repository.PagoRepository;
import com.alquilerautos.exception.ResourceNotFoundException;
import com.alquilerautos.exception.ValidationException;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository repo;
    private final Validator validator;

    public PagoService(PagoRepository repo, Validator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    private void validatePago(Pago p) {
        Set<ConstraintViolation<Pago>> violations = validator.validate(p);
        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }

        // Validar que el monto sea consistente con la reserva
        if (p.getReserva() != null && p.getMonto() != null) {
            long diasReserva = p.getReserva().getFechaFin().toEpochDay() - 
                             p.getReserva().getFechaInicio().toEpochDay();
            double montoEsperado = p.getReserva().getVehiculo().getPrecioAlquilerDia() * diasReserva;
            if (Math.abs(p.getMonto() - montoEsperado) > 0.01) {
                throw new ValidationException("El monto del pago no coincide con el cálculo de la reserva");
            }
        }
    }

    public Pago save(Pago p) {
        validatePago(p);
        return repo.save(p);
    }

    public List<Pago> getAll() {
        return repo.findAll();
    }

    public Pago update(Long id, Pago nuevo) {
        Pago p = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pago", "id", id));
        
        p.setReserva(nuevo.getReserva());
        p.setMonto(nuevo.getMonto());
        p.setFechaPago(nuevo.getFechaPago());
        p.setMetodoPago(nuevo.getMetodoPago());
        p.setEstado(nuevo.getEstado());

        validatePago(p);
        return repo.save(p);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Pago", "id", id);
        }
        repo.deleteById(id);
    }
}
