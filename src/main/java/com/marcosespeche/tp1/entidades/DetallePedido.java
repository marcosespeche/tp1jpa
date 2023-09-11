package com.marcosespeche.tp1.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetallePedido extends EntidadBase {

    private int cantidad;

    private double subtotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FKproducto")
    private Producto producto;
}