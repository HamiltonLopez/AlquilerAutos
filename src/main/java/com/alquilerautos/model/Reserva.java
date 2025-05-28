package com.alquilerautos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne
    private Usuario usuario;

    @NotNull(message = "El vehículo es obligatorio")
    @ManyToOne
    private Vehiculo vehiculo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio debe ser actual o futura")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser futura")
    private LocalDate fechaFin;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(PENDIENTE|ACTIVA|COMPLETADA|CANCELADA)$", 
            message = "El estado debe ser PENDIENTE, ACTIVA, COMPLETADA o CANCELADA")
    private String estado;
}