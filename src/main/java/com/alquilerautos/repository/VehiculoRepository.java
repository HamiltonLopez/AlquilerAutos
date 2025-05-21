package com.alquilerautos.repository;

import com.alquilerautos.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByPlacaContainingIgnoreCase(String placa);
}