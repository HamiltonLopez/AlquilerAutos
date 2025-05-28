package com.alquilerautos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La reserva es obligatoria")
    @ManyToOne
    private Reserva reserva;
    
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDateTime fechaPago;

    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "^(EFECTIVO|TARJETA|TRANSFERENCIA)$", 
            message = "El método de pago debe ser EFECTIVO, TARJETA o TRANSFERENCIA")
    private String metodoPago;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(PENDIENTE|COMPLETADO|CANCELADO)$", 
            message = "El estado debe ser PENDIENTE, COMPLETADO o CANCELADO")
    private String estado;
}

