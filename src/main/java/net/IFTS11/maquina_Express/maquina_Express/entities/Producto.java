package net.IFTS11.maquina_Express.maquina_Express.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String producto;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maquina_id")
    @JsonIgnoreProperties({"productos", "handler", "hibernateLazyInitializer"})
    private Maquina maquina;
    private int cantidad;
    private float precio;
    private boolean activo;
    private String imagen;
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

    public Producto(long id, String producto, Maquina maquina, int cantidad, float precio, boolean activo, String imagen, Date fechavencimiento, Date fechareposicion, Date fechaactualizacion, String userService) {
        this.id = id;
        this.producto = producto;
        this.maquina = maquina;
        this.cantidad = cantidad;
        this.precio = precio;
        this.activo = activo;
        this.imagen = imagen;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getImage() {
        return imagen;
    }

    public void setImage(String image) {
        this.imagen = image;
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

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", producto='" + producto + '\'' +
                ", maquina=" + maquina +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", activo=" + activo +
                ", imagen='" + imagen + '\'' +
                ", fechavencimiento=" + fechavencimiento +
                ", fechareposicion=" + fechareposicion +
                ", fechaactualizacion=" + fechaactualizacion +
                ", userService='" + userService + '\'' +
                '}';
    }
}
