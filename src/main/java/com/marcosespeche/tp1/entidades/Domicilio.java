package com.marcosespeche.tp1.entidades;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Domicilio extends EntidadBase {

    private String calle;

    private String numero;

    private String localidad;
}