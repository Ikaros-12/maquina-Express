package net.IFTS11.maquina_Express.maquina_Express.dto;

import net.IFTS11.maquina_Express.maquina_Express.entities.Factura;

import java.util.Date;

public class FacturacionDto {
    long id;

    Date fecha;

    float precio;
    String producto;

    public FacturacionDto() {
    }

    public FacturacionDto(long id, Date fecha, float precio, String producto) {
        this.id = id;
        this.fecha = fecha;
        this.precio = precio;
        this.producto = producto;
    }

    public FacturacionDto(Factura factura) {
        this.id = factura.getId();
        this.fecha = factura.getFecha_creacion();
        this.precio = factura.getPrecio();
        this.producto = factura.getProducto_nombre();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
