package net.IFTS11.maquina_Express.maquina_Express.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import net.IFTS11.maquina_Express.maquina_Express.entities.Producto;

public class ProductosDto {

    private long id;
    private String producto;
    private float precio;
    private String imagen;

    public ProductosDto() {
    }

    public ProductosDto(long id, String producto, float precio, String imagen) {
        this.id = id;
        this.producto = producto;
        this.precio = precio;
        this.imagen = imagen;
    }

    public ProductosDto(Producto producto) {
        this.id = producto.getId();
        this.producto = producto.getProducto();
        this.precio = producto.getPrecio();
        this.imagen = producto.getImagen();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
