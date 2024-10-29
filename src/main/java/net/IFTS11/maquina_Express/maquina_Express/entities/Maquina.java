package net.IFTS11.maquina_Express.maquina_Express.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="maquinas")
public class Maquina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("alias_name")
    private String alias;
    private String modelo;
    private String marca;
    private String tipo;
    private String estado;
    private String url;

    @JsonIgnoreProperties({"productos", "handler", "hibernateLazyInitializer"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "maquina", cascade = CascadeType.ALL)
    private List<Producto> productos;

    @Column(name="cola_MQ")
    private String colaMQ;

    public Maquina() {
        productos=new ArrayList<>();
    }

    public Maquina(String alias, String modelo, String marca, String tipo, String estado, String url) {
        this.alias = alias;
        this.modelo = modelo;
        this.marca = marca;
        this.tipo = tipo;
        this.estado = estado;
        this.url = url;
        productos=new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColaMQ() {
        return colaMQ;
    }

    public void setColaMQ(String colaMQ) {
        this.colaMQ = colaMQ;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        return "Maquina{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                ", url='" + url + '\'' +
                ", productos=" + productos +
                ", colaMQ='" + colaMQ + '\'' +
                '}';
    }
}
