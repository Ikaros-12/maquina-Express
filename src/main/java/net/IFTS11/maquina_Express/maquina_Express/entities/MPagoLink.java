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

    public MPagoLink() {
    }

    public MPagoLink(long id, int id_producto, int id_maquina, float precio, String linkMercadoPago) {
        this.id = id;
        this.id_producto = id_producto;
        this.id_maquina = id_maquina;
        this.precio = precio;
        this.linkMercadoPago = linkMercadoPago;
        this.estado="confirmar";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(int id_maquina) {
        this.id_maquina = id_maquina;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getLinkMercadoPago() {
        return linkMercadoPago;
    }

    public void setLinkMercadoPago(String linkMercadoPago) {
        this.linkMercadoPago = linkMercadoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
