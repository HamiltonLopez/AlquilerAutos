package com.alquilerautos.service;

import com.alquilerautos.model.Reserva;
import com.alquilerautos.repository.ReservaRepository;
import com.alquilerautos.exception.ResourceNotFoundException;
import com.alquilerautos.exception.ValidationException;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository repo;
    private final Validator validator;

    public ReservaService(ReservaRepository repo, Validator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    private void validateReserva(Reserva r) {
        Set<ConstraintViolation<Reserva>> violations = validator.validate(r);
        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }

        // Validar que la fecha de fin sea posterior a la fecha de inicio
        if (r.getFechaFin() != null && r.getFechaInicio() != null && 
            !r.getFechaFin().isAfter(r.getFechaInicio())) {
            throw new ValidationException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        // Validar que el vehículo esté disponible
        if (r.getVehiculo() != null && !r.getVehiculo().isDisponible()) {
            throw new ValidationException("El vehículo seleccionado no está disponible");
        }
    }

    public Reserva save(Reserva r) {
        validateReserva(r);
        return repo.save(r);
    }

    public List<Reserva> getAll() {
        return repo.findAll();
    }

    public Reserva update(Long id, Reserva nueva) {
        Reserva r = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reserva", "id", id));
        
        r.setUsuario(nueva.getUsuario());
        r.setVehiculo(nueva.getVehiculo());
        r.setFechaInicio(nueva.getFechaInicio());
        r.setFechaFin(nueva.getFechaFin());
        r.setEstado(nueva.getEstado());

        validateReserva(r);
        return repo.save(r);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Reserva", "id", id);
        }
        repo.deleteById(id);
    }
}
