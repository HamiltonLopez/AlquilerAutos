package com.alquilerautos.service;

import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.repository.VehiculoRepository;
import com.alquilerautos.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setAnio(2022);
        vehiculo.setPlaca("ABC123");
        vehiculo.setPrecioAlquilerDia(50.0);
        vehiculo.setDisponible(true);
    }

    @Test
    void whenSaveVehiculo_thenReturnVehiculo() {
        when(validator.validate(any())).thenReturn(new HashSet<>());
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo saved = vehiculoService.save(vehiculo);

        assertNotNull(saved);
        assertEquals("Toyota", saved.getMarca());
        assertEquals("Corolla", saved.getModelo());
        assertEquals("ABC123", saved.getPlaca());
        assertEquals(50.0, saved.getPrecioAlquilerDia());
        assertTrue(saved.isDisponible());
        verify(vehiculoRepository).save(any(Vehiculo.class));
        verify(validator).validate(any());
    }

    @Test
    void whenGetAll_thenReturnList() {
        List<Vehiculo> vehiculos = Arrays.asList(vehiculo);
        when(vehiculoRepository.findAll()).thenReturn(vehiculos);

        List<Vehiculo> found = vehiculoService.getAll();

        assertNotNull(found);
        assertEquals(1, found.size());
        verify(vehiculoRepository).findAll();
    }

    @Test
    void whenSearchByPlaca_thenReturnVehiculo() {
        List<Vehiculo> vehiculos = Arrays.asList(vehiculo);
        when(vehiculoRepository.findByPlacaContainingIgnoreCase(anyString())).thenReturn(vehiculos);

        List<Vehiculo> found = vehiculoService.searchByPlaca("ABC");

        assertNotNull(found);
        assertEquals(1, found.size());
        assertEquals("ABC123", found.get(0).getPlaca());
        verify(vehiculoRepository).findByPlacaContainingIgnoreCase(anyString());
    }

    @Test
    void whenUpdateVehiculo_thenReturnUpdatedVehiculo() {
        Vehiculo nuevoVehiculo = new Vehiculo();
        nuevoVehiculo.setMarca("Honda");
        nuevoVehiculo.setModelo("Civic");
        nuevoVehiculo.setAnio(2023);
        nuevoVehiculo.setPlaca("XYZ789");
        nuevoVehiculo.setPrecioAlquilerDia(60.0);
        nuevoVehiculo.setDisponible(false);

        when(validator.validate(any())).thenReturn(new HashSet<>());
        when(vehiculoRepository.findById(anyLong())).thenReturn(Optional.of(vehiculo));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(nuevoVehiculo);

        Vehiculo updated = vehiculoService.update(1L, nuevoVehiculo);

        assertNotNull(updated);
        assertEquals("Honda", updated.getMarca());
        assertEquals("Civic", updated.getModelo());
        assertEquals("XYZ789", updated.getPlaca());
        assertEquals(60.0, updated.getPrecioAlquilerDia());
        assertFalse(updated.isDisponible());
        verify(vehiculoRepository).findById(anyLong());
        verify(vehiculoRepository).save(any(Vehiculo.class));
        verify(validator).validate(any());
    }

    @Test
    void whenUpdateVehiculoNotFound_thenThrowException() {
        when(vehiculoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            vehiculoService.update(1L, new Vehiculo());
        });

        verify(vehiculoRepository).findById(anyLong());
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    void whenDeleteVehiculo_thenVerifyRepositoryCall() {
        when(vehiculoRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(vehiculoRepository).deleteById(anyLong());

        vehiculoService.delete(1L);

        verify(vehiculoRepository).existsById(1L);
        verify(vehiculoRepository).deleteById(1L);
    }

    @Test
    void whenDeleteVehiculoNotFound_thenThrowException() {
        when(vehiculoRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            vehiculoService.delete(1L);
        });

        verify(vehiculoRepository).existsById(1L);
        verify(vehiculoRepository, never()).deleteById(anyLong());
    }
} 