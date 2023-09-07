package com.marcosespeche.tp1.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido extends EntidadBase {

    private String estado;

    private String tipoEnvio;

    private Date fecha;

    private double total;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FKfactura")
    private Factura factura;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FKpedido")
    private List<DetallePedido> listaDetallePedidos = new ArrayList<>();

    public void anadirDetalle(DetallePedido detallePedido){
        this.listaDetallePedidos.add(detallePedido);
    }
}