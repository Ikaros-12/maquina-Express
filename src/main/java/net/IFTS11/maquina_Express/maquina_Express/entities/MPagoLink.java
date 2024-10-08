package net.IFTS11.maquina_Express.maquina_Express.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "LOG_MP_GENERATION_LINK")
public class MPagoLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int id_producto;
    private int id_maquina;
    private float precio;
    private String linkMercadoPago;
    private String estado;
}
