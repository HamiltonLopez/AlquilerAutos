package com.alquilerautos.service;

import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.repository.VehiculoRepository;
import com.alquilerautos.exception.ResourceNotFoundException;
import com.alquilerautos.exception.ValidationException;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VehiculoService {

    private final VehiculoRepository repo;
    private final Validator validator;

    public VehiculoService(VehiculoRepository repo, Validator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    private void validateVehiculo(Vehiculo v) {
        Set<ConstraintViolation<Vehiculo>> violations = validator.validate(v);
        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }

        // Validar placa única
        if (v.getId() == null && !repo.findByPlacaContainingIgnoreCase(v.getPlaca()).isEmpty()) {
            throw new ValidationException("Ya existe un vehículo con esta placa");
        }
    }

    public Vehiculo save(Vehiculo v) {
        validateVehiculo(v);
        return repo.save(v);
    }

    public List<Vehiculo> getAll() {
        return repo.findAll();
    }

    public List<Vehiculo> searchByPlaca(String placa) {
        return repo.findByPlacaContainingIgnoreCase(placa);
    }

    public Vehiculo update(Long id, Vehiculo nuevo) {
        Vehiculo v = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vehículo", "id", id));
        
        v.setMarca(nuevo.getMarca());
        v.setModelo(nuevo.getModelo());
        v.setPlaca(nuevo.getPlaca());
        v.setAnio(nuevo.getAnio());
        v.setPrecioAlquilerDia(nuevo.getPrecioAlquilerDia());
        v.setDisponible(nuevo.isDisponible());

        validateVehiculo(v);
        return repo.save(v);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Vehículo", "id", id);
        }
        repo.deleteById(id);
    }
}
