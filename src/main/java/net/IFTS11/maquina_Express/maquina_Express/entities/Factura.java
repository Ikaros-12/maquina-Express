package net.IFTS11.maquina_Express.maquina_Express.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "codigo_afip")
    private String codigoAFIP;
    private String producto;

    @Column(name = "alias_maquina")
    private String aliasMaquina;
    private float precio;


}
