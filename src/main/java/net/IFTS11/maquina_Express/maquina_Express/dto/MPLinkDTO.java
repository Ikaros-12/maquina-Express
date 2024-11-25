package net.IFTS11.maquina_Express.maquina_Express.dto;

import net.IFTS11.maquina_Express.maquina_Express.entities.MPagos;

import java.util.Date;

public class MPLinkDTO {
    long id;

    Date fecha;

    Float precio;

    String estado;

    String link;

    public MPLinkDTO() {
    }

    public MPLinkDTO(long id, Date fecha, Float precio, String estado, String link) {
        this.id = id;
        this.fecha = fecha;
        this.precio = precio;
        this.estado = estado;
        this.link= link;
    }

    public MPLinkDTO(MPagos mPagos) {
        this.id = mPagos.getId();
        this.fecha = mPagos.getFecha_creacion();
        this.precio = mPagos.getPrecio();
        this.estado = mPagos.getEstado();
        this.link = mPagos.getLinkMercadoPago();
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

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
