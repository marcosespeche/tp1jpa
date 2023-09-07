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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rubro extends EntidadBase {

    private String denominacion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FKrubro")
    private List<Producto> listaProductos = new ArrayList<>();

    public void anadirProducto(Producto producto) {
        this.listaProductos.add(producto);
    }

}
