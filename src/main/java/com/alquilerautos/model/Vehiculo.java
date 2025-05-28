package com.alquilerautos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.Year;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotBlank(message = "La placa es obligatoria")
    @Column(unique = true)
    @Pattern(regexp = "^[A-Z0-9]{6,7}$", message = "La placa debe tener formato válido (6-7 caracteres alfanuméricos)")
    private String placa;

    @Min(value = 2008, message = "El año debe ser mayor o igual a 2008")
    @Max(value = 2024, message = "El año no puede ser mayor al año actual")
    private Integer anio;

    @Positive(message = "El precio de alquiler por día debe ser mayor a 0")
    private double precioAlquilerDia;

    private boolean disponible;
}