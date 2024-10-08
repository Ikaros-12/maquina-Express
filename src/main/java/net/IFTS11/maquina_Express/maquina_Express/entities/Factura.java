package net.IFTS11.maquina_Express.maquina_Express.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String codigoAFIP;
    private String producto;
    private Maquina maquina;
    private float precio;


}
