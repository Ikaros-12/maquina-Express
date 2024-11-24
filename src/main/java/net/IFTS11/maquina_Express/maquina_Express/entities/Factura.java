package net.IFTS11.maquina_Express.maquina_Express.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maquina_id")
    @JsonIgnoreProperties({"facturas", "handler", "hibernateLazyInitializer"})
    private Maquina maquina;
    private float precio;


}
