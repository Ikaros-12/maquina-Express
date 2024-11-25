package net.IFTS11.maquina_Express.maquina_Express.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "codigo_afip")
    private String codigoAFIP;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"facturas", "handler", "hibernateLazyInitializer"})
    private Producto producto;
    private String producto_nombre;

    private Date fecha_creacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maquina_id")
    @JsonIgnoreProperties({"facturas", "handler", "hibernateLazyInitializer"})
    private Maquina maquina;
    private float precio;

    public Factura() {
    }

    public Factura(long id, String codigoAFIP, String producto_nombre, String fecha_creacion, float precio, Maquina maquina, Producto producto) {
        this.id = id;
        this.codigoAFIP = codigoAFIP;
        this.producto_nombre = producto_nombre;
        this.fecha_creacion = new Date();
        this.precio = precio;
        this.maquina = maquina;
        this.producto = producto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigoAFIP() {
        return codigoAFIP;
    }

    public void setCodigoAFIP(String codigoAFIP) {
        this.codigoAFIP = codigoAFIP;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getProducto_nombre() {
        return producto_nombre;
    }

    public void setProducto_nombre(String producto_nombre) {
        this.producto_nombre = producto_nombre;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String obtFecha(){
        String pattern = "yyyyMMdd";
        return DateFormatUtils.format(this.fecha_creacion,"yyyyMMdd");
    }


}
