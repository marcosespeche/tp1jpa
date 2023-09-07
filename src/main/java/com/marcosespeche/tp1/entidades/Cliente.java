package com.marcosespeche.tp1.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente extends EntidadBase {

    private String nombre;

    private String apellido;

    private String telefono;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FKcliente")
    private List<Pedido> listaPedidos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FKcliente")
    private List<Domicilio> listaDomicilios = new ArrayList<>();

    public void anadirDomicilio(Domicilio domicilio){
        this.listaDomicilios.add(domicilio);
    }

    public void anadirPedido(Pedido pedido){
        this.listaPedidos.add(pedido);
    }
}