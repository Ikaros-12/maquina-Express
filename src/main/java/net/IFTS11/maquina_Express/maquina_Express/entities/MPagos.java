package net.IFTS11.maquina_Express.maquina_Express.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "LOG_MP_GENERATION_LINK")
public class MPagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"LOG_MP_GENERATION_LINK", "handler", "hibernateLazyInitializer"})
    private Producto producto;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maquina_id")
    @JsonIgnoreProperties({"LOG_MP_GENERATION_LINK", "handler", "hibernateLazyInitializer"})
    private Maquina maquina;
    private float precio;
    private String linkMercadoPago;
    private String estado;

    public MPagos() {
    }

    public MPagos(long id, Producto producto, Maquina maquina, float precio, String linkMercadoPago) {
        this.id = id;
        this.producto = producto;
        this.maquina = maquina;
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

    public Producto getproducto() {
        return producto;
    }

    public void setproducto(Producto producto) {
        this.producto = producto;
    }

    public Maquina getmaquina() {
        return maquina;
    }

    public void setId_maquina(Maquina maquina) {
        this.maquina = maquina;
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
