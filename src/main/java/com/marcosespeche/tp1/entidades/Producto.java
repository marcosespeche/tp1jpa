package com.marcosespeche.tp1.entidades;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Producto extends EntidadBase {

    private String manufacturado;

    private int tiempoEstimadoCocina;

    private String denominacion;

    private double precioVenta;

    private double precioCompra;

    private int stockActual;

    private int stockMinimo;

    private String unidadMedida;

    private String receta;
}
