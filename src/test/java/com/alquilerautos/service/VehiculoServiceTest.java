package com.alquilerautos.service;

import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.repository.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

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
        vehiculo.setTipo("Sedán");
    }

    @Test
    void whenSaveVehiculo_thenReturnVehiculo() {
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo saved = vehiculoService.save(vehiculo);

        assertNotNull(saved);
        assertEquals("Toyota", saved.getMarca());
        assertEquals("Corolla", saved.getModelo());
        verify(vehiculoRepository).save(any(Vehiculo.class));
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
        nuevoVehiculo.setTipo("Sedán");

        when(vehiculoRepository.findById(anyLong())).thenReturn(Optional.of(vehiculo));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(nuevoVehiculo);

        Vehiculo updated = vehiculoService.update(1L, nuevoVehiculo);

        assertNotNull(updated);
        assertEquals("Honda", updated.getMarca());
        assertEquals("Civic", updated.getModelo());
        verify(vehiculoRepository).findById(anyLong());
        verify(vehiculoRepository).save(any(Vehiculo.class));
    }

    @Test
    void whenDeleteVehiculo_thenVerifyRepositoryCall() {
        vehiculoService.delete(1L);
        verify(vehiculoRepository).deleteById(1L);
    }
} 