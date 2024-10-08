package net.IFTS11.maquina_Express.maquina_Express.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String producto;
    @ManyToOne
    private Maquina maquina;
    private int cantidad;
    private float precio;
    private boolean active;
    private String image;
    @Column(name = "fecha_vencimiento")
    private Date fechavencimiento;
    @Column(name = "fecha_reposicion")
    private Date fechareposicion;

    @Column(name = "fecha_actualizacion")
    private Date fechaactualizacion;

    @Column(name = "user_service")
    private String userService;

    public Producto() {
    }

    public Producto(long id, String producto, Maquina maquina, int cantidad, float precio, boolean active, String image, Date fechavencimiento, Date fechareposicion, Date fechaactualizacion, String userService) {
        this.id = id;
        this.producto = producto;
        this.maquina = maquina;
        this.cantidad = cantidad;
        this.precio = precio;
        this.active = active;
        this.image = image;
        this.fechavencimiento = fechavencimiento;
        this.fechareposicion = fechareposicion;
        this.fechaactualizacion = fechaactualizacion;
        this.userService = userService;
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

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public Date getFechareposicion() {
        return fechareposicion;
    }

    public void setFechareposicion(Date fechareposicion) {
        this.fechareposicion = fechareposicion;
    }

    public Date getFechaactualizacion() {
        return fechaactualizacion;
    }

    public void setFechaactualizacion(Date fechaactualizacion) {
        this.fechaactualizacion = fechaactualizacion;
    }

    public String getUserService() {
        return userService;
    }

    public void setUserService(String userService) {
        this.userService = userService;
    }
}
