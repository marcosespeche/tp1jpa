package com.marcosespeche.tp1.entidades;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura extends EntidadBase {

    private int numero;

    private String formaPago;

    private Date fecha;

    private double descuento;

    private int total;
}